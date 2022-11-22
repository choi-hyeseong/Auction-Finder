package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionDetail;
import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.DetailDate;
import com.comet.auctionfinder.util.DetailList;
import com.comet.auctionfinder.util.Twin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class AuctionParser {

    private AuctionCache cache;
    private static final String AUCTION_URL = "https://www.courtauction.go.kr/";
    private static final String CITY_URL = "https://www.courtauction.go.kr/RetrieveAucSigu.ajax";


    @Async
    public CompletableFuture<Twin<AuctionResponse, List<AuctionSimple>>> parseData(String province, String city) {
        ChromeDriver driver = null;
        List<AuctionSimple> result = new ArrayList<>();
        Twin<String, String> input = Twin.of(province, city);
        try {
            //check cache
            if (cache.isCacheExist(input))
                return CompletableFuture.completedFuture(Twin.of(AuctionResponse.FOUND, cache.getCache(input)
                        .orElseThrow()));
            //load logic
            driver = new ChromeDriver(getOptions());
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            driver.get(AUCTION_URL);
            String main = driver.getWindowHandle();
            int click = 0;
            try {
                wait.until((dri) -> dri.getWindowHandles().size() > 1); //팝업 대기
                for (String handle : driver.getWindowHandles())
                    closePopup(driver, handle, main);
            }
            catch (TimeoutException e) {
                log.info("not detected popup");
            }
            driver.switchTo().window(main);
            //프레임 진짜 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 이거 보니까 프레임만 변화되는거 같은데?
            driver.switchTo().frame(driver.findElement(By.name("indexFrame")));
            Select proSelect = new Select(driver.findElement(By.id("idSidoCode1")));
            for (int i = 0; i < proSelect.getOptions().size(); i++) {
                String option = proSelect.getOptions().get(i).getText();
                if (option.equalsIgnoreCase(province)) {
                    click = i;
                    break;
                }
            }
            proSelect.selectByIndex(click);
            click = -1;
            Select citySelect = new Select(driver.findElement(By.id("idSiguCode1")));
            if (!city.equals("전체")) {
                for (int i = 0; i < citySelect.getOptions().size(); i++) {
                    String option = citySelect.getOptions().get(i).getText();
                    if (option.equals(city))
                        click = i;
                }
                if (click == -1) {
                    //옵션에 존재하지 않을경우
                    return CompletableFuture.completedFuture(Twin.of(AuctionResponse.CITY_NOT_FOUND, result));
                }
                citySelect.selectByIndex(click);
            }

            //driver.findElement(By.id("main_btn")).click(); //검색창
            driver.executeScript("fastSrch()"); //검색 로드
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //검색 결과창
            new Select(driver.findElement(By.id("ipage"))).selectByIndex(3); //40페이지씩 로드
            while (true) {
                //무한루프 (페이지가 끝일때까지.)
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("page2")));
                //parse logic
                List<WebElement> elements = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div[3]/div[4]/form[1]/table/tbody")); //해당 페이지 경매품목
                for (WebElement element : elements) {
                    result.addAll(parseDataFromElement(element));
                }

                //next page logic
                WebElement pageDiv = driver.findElement(By.className("page2"));
                List<WebElement> pages = pageDiv.findElements(By.xpath("*"));
                boolean next = false;
                for (WebElement page : pages) {

                    if (next) {
                        next = false;
                        page.click();
                        break;
                    }
                    else if (page.getTagName().contains("span")) {
                        //현재페이지인경우.
                        next = true;
                    }
                }
                if (next)
                    break; //while break
            }
            cache.putCache(input, result); //캐시 저장
            //driver.close(); //끄지마... => 창이 하나만 남아있을경우 셀레니움 전체를 종료하는 quit이랑 동일하게 수행됨..
        }
        catch (Exception e) { //모든 예외가 발생했을경우
            e.printStackTrace();
            CompletableFuture.completedFuture(Twin.of(AuctionResponse.INTERNAL_ERROR, result));
        }
        finally {
            if (driver != null)
                driver.quit(); //멀티쓰레딩을 위한 객체 사용후 종료
        }

        return result.size() != 0 ? CompletableFuture.completedFuture(Twin.of(AuctionResponse.FOUND, result)) : CompletableFuture.completedFuture(Twin.of(AuctionResponse.NO_CONTENTS, result));
    }

    @Async
    public CompletableFuture<Twin<AuctionResponse, Optional<AuctionDetail>>> parseDetail(String court, String auctionValue) {
        ChromeDriver driver = null;
        Twin<String, String> key = Twin.of(court, auctionValue);
        if (cache.isCacheExist(key))
            return CompletableFuture.completedFuture(Twin.of(AuctionResponse.FOUND, cache.getDetailCache(key)));
        else {
            try {
                //load logic
                driver = new ChromeDriver(getOptions());
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                driver.get(AUCTION_URL);
                String main = driver.getWindowHandle();
                int click = 0;
                try {
                    wait.until((dri) -> dri.getWindowHandles().size() > 1); //팝업 대기
                    for (String handle : driver.getWindowHandles())
                        closePopup(driver, handle, main);
                }
                catch (TimeoutException e) {
                    log.info("not detected popup");
                }

                driver.switchTo().window(main);
                //프레임 내부 자바스크립트 미동작..?
                driver.switchTo().frame(driver.findElement(By.name("indexFrame")));
                driver.executeScript("porActSubmit(\"\",\"InitMulSrch.laf\",\"\",\"PNO102001\")");
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //검색 결과창
                Select courtSelect = new Select(driver.findElement(By.id("idJiwonNm")));
                String[] auctionSplit = auctionValue.split("타경");
                courtSelect.selectByValue(court);
                Select auctionSelect = new Select(driver.findElement(By.id("idSaYear")));
                auctionSelect.selectByValue(auctionSplit[0]);
                WebElement detailValue = driver.findElement(By.id("idSaSer"));
                detailValue.click();
                detailValue.sendKeys(auctionSplit[1]);
                driver.executeScript("srch()"); //검색 로드
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //검색 결과창
                List<WebElement> elements = driver.findElement(By.className("txtleft")).findElements(By.xpath("*"));
                for (WebElement element : elements) {
                    WebElement inner = element.findElement(By.xpath("*"));
                    String attribute = inner.getAttribute("onclick");
                    if (attribute != null) {
                        driver.executeScript(attribute.trim().replace("javascript:", ""));
                        break;
                    }
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                AuctionDetail result = parseDetailFromElement(driver, driver.findElement(By.id("contents")));
                cache.putDetailCache(key, result);
                return CompletableFuture.completedFuture(Twin.of(AuctionResponse.FOUND, Optional.of(result)));
            }
            catch (Exception e) {
                e.printStackTrace();
                return CompletableFuture.completedFuture(Twin.of(AuctionResponse.INTERNAL_ERROR, Optional.empty()));
            }
            finally {
                if (driver != null)
                    driver.quit(); //멀티쓰레딩을 위한 객체 사용후 종료
            }
        }
    }

    //경북 -> 경상북도
    //default가 맨위에 있으면 얘 먼저 파싱됨.. 아닌가
    public String matchProvince(String pro) {
        pro = pro.trim(); //띄어쓰기 하나때문에 ㅎㅎ....
        if (pro.contains("시") || pro.contains("도")) //이미 파싱된 정보
            return pro;
        return switch (pro) {
            case "부산" -> "부산광역시";
            case "대구" -> "대구광역시";
            case "인천" -> "인천광역시";
            case "광주" -> "광주광역시";
            case "대전" -> "대전광역시";
            case "울산" -> "울산광역시";
            case "세종" -> "세종특별자치시";
            case "경기" -> "경기도";
            case "강원" -> "강원도";
            case "충북" -> "충청북도";
            case "충남" -> "충청남도";
            case "전북" -> "전라북도";
            case "전남" -> "전라남도";
            case "경북" -> "경상북도";
            case "경남" -> "경상남도";
            case "제주" -> "제주도";
            default -> "서울특별시"; //파싱 안될경우

        };
    }

    private void closePopup(WebDriver driver, String handle, String main) {
        if (!handle.contentEquals(main))
            driver.switchTo()
                    .window(handle)
                    .close();
    }

    private AuctionDetail parseDetailFromElement(ChromeDriver driver, WebElement body) {
        //content body
        String auctionValue = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[1]/td[1]"))
                .getText();
        String auctionNumber = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[1]/td[2]"))
                .getText();
        String type = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[1]/td[3]")).getText();
        String checkValue = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[2]/td[1]"))
                .getText();
        String minimumValue = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[2]/td[2]"))
                .getText();
        String auctionType = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[2]/td[3]"))
                .getText();
        String endDate = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[3]/td")).getText();
        String extra = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody/tr[4]/td")).getText();
        List<String> areas = new ArrayList<>();
        String part = "";
        List<WebElement> list = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[1]/tbody"))
                .findElements(By.xpath("*"));
        for (WebElement element : list) {
            List<WebElement> innerList = element.findElements(By.xpath("*"));
            if (innerList.get(0).getText().contains("소재지"))
                areas.add(innerList.get(1).getText());
            else if (innerList.get(0).getText().contains("담당"))
                part = innerList.get(1).getText();
        }
        String startDate = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[2]/tbody/tr[1]/td[1]"))
                .getText();
        String auctionDate = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[2]/tbody/tr[1]/td[2]"))
                .getText();
        String bidDate = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[2]/tbody/tr[2]/td[1]"))
                .getText();
        String askBid = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/table[2]/tbody/tr[2]/td[2]")).getText();
        String imageExtra = body.findElement(By.xpath("//*[@id=\"contents\"]/div[4]/div[2]/table/tbody/tr/th"))
                .getText();
        List<WebElement> dateElement = body.findElement(By.xpath("//*[@id=\"contents\"]/div[5]/table/tbody"))
                .findElements(By.xpath("*")); //tr
        List<DetailDate> detailDates = new ArrayList<>();
        for (WebElement date : dateElement) {
            List<WebElement> res = date.findElements(By.xpath("*"));
            DetailDate detailDate = DetailDate.builder().date(res.get(0).getText()).type(res.get(1).getText())
                    .location(res.get(2).getText()).minimum(res.get(3).getText()).result(res.get(4).getText()).build();
            detailDates.add(detailDate);
        }
        List<WebElement> listElement = body.findElement(By.xpath("//*[@id=\"contents\"]/div[6]/table/tbody"))
                .findElements(By.xpath("*")); //tr
        List<DetailList> detailLists = new ArrayList<>();
        for (WebElement date : listElement) {
            List<WebElement> res = date.findElements(By.xpath("*"));
            DetailList detailList = DetailList.builder().number(res.get(0).getText()).type(res.get(1).getText())
                    .detail(res.get(2).getText()).build();
            detailLists.add(detailList);
        }
        String summary = body.findElement(By.xpath("//*[@id=\"contents\"]/div[7]/table/tbody/tr/td")).getText();
        //image Logic
        List<String> images = new ArrayList<>();
        Optional<WebElement> optional = body.findElements(By.tagName("a")).stream().filter((element) -> {
            String inner = element.getAttribute("onclick");
            return inner != null && inner.contains("showPhotoPopup");
        }).findFirst();
        if (optional.isPresent()) {
            WebElement imageElement = optional.get();
            driver.executeScript(imageElement.getAttribute("onclick").trim().replace("javascript:", ""));
            //사진 파싱 팝업.
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //기다림
            String main = driver.getWindowHandle();
            try {
                for (String handle : driver.getWindowHandles())
                    if (!handle.equals(main)) {
                        driver.switchTo().window(handle); //여기서부터 파싱 가능
                        while (true) {
                            //무한루프 (페이지가 끝일때까지.)
                            //next page logic
                            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //기다림
                            WebElement pageDiv = driver.findElement(By.className("page2"));
                            List<WebElement> pages = pageDiv.findElements(By.xpath("*"));
                            WebElement srcElement = driver.findElement(By.xpath("//*[@id=\"pop_contents_1\"]/form/div[2]/table/tbody/tr/td/img"));
                            images.add(srcElement.getAttribute("src"));
                            boolean next = false;
                            for (WebElement page : pages) {

                                if (next) {
                                    next = false;
                                    page.click();
                                    break;
                                }
                                else if (page.getTagName().contains("span")) {
                                    //현재페이지인경우.
                                    next = true;
                                }
                            }
                            if (next)
                                break; //while break
                        }
                        break;
                    }
            }
            catch (TimeoutException e) {
                log.info("not detected popup");
            }
            driver.switchTo().window(main);
        }


        return AuctionDetail.builder().auctionValue(auctionValue).auctionNumber(auctionNumber)
                .type(type).checkValue(checkValue).minimumValue(minimumValue).auctionType(auctionType).endDate(endDate)
                .extra(extra).areas(areas).part(part).startDate(startDate).auctionDate(auctionDate).bidDate(bidDate)
                .askBid(askBid).imageExtra(imageExtra).images(images).dates(detailDates).list(detailLists)
                .summary(summary).build();

    }

    private List<AuctionSimple> parseDataFromElement(WebElement tbody) throws ParseException {
        List<AuctionSimple> result = new ArrayList<>();
        List<WebElement> trList = tbody.findElements(By.tagName("tr")); //한 행
        for (WebElement tr : trList) {
            List<WebElement> tdList = tr.findElements(By.tagName("td"));
            tdList.remove(0); //첫번째는 버튼 클릭
            String[] courtVal = tdList.get(0).getText().split("\n");
            String court = courtVal[0];
            String auctionNumber = courtVal[1];
            String type = tdList.get(1).getText().replace("\n", "");
            List<Twin<String, String>> area = new ArrayList<>(); //지역 목록
            List<WebElement> childArea = tdList.get(2).findElements(By.tagName("div")); //지역 목록
            for (WebElement child : childArea) {
                String[] loc = child.getText().split("\n");
                area.add(Twin.of(loc[0], loc[1]));
            }
            String extra = tdList.get(3).getText();
            String[] houseValue = tdList.get(4).getText().replace(",", "").split("\n");
            long checkValue = Long.parseLong(houseValue[0]);
            long minimumValue = Long.parseLong(houseValue[1]);
            String[] lastValue = tdList.get(5).getText().trim().split("\n");
            String part = lastValue[0];
            Date until = new SimpleDateFormat("yyyy.MM.dd").parse(lastValue[1]);
            String status = lastValue[2];
            result.add(AuctionSimple.builder().court(court).auctionNumber(auctionNumber).type(type).area(area)
                    .extra(extra)
                    .checkValue(checkValue).minimumValue(minimumValue).part(part).until(until).status(status)
                    .build());
        }
        return result;
    }

    @Async
    public CompletableFuture<List<String>> getCities(String province) {
        //짜증나서 파싱한다..
        List<String> cities = new ArrayList<>();
        try {
            int parse = parseCityToInt(province);
            if (parse != -1) {
                //지역 파싱이 된경우.
                HttpClient client = HttpClient.newHttpClient();
                String result = client.sendAsync(HttpRequest.newBuilder(new URI(CITY_URL))
                                .POST(HttpRequest.BodyPublishers.ofString("index=FB&sidoCode=" + parse + "&id2=idSiguCode1&id3=idDongCode1"))
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .build(), HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body).get(); //파싱은 된듯.
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(result)));
                NodeList list = doc.getElementsByTagName("option");
                List<String> strList = new ArrayList<>();
                for (int i = 0; i < list.getLength(); i++)
                    strList.add(list.item(i).getChildNodes().item(1).toString());
                strList.remove(strList.size() - 1);
                strList.remove(0);
                //첫번째와 끝값은 쓰레기값
                cities.addAll(strList.stream().distinct()
                        .map((data) -> data.replace("[#cdata-section: ", "").replace("]", "").trim()).toList());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(cities);
    }

    private ChromeOptions getOptions() {
        return new ChromeOptions()
                .addArguments("--disable-gpu")
                .addArguments("--disable-logging")
                .addArguments("headless")
                .addArguments("--disable-popup-blocking")
                .addArguments("--blink-settings=imagesEnabled=false");
    }

    private int parseCityToInt(String province) {
        province = province.trim();
        return switch (province) {
            case "서울" -> 11;
            case "부산" -> 26;
            case "대구" -> 27;
            case "인천" -> 28;
            case "광주" -> 29;
            case "대전" -> 30;
            case "울산" -> 31;
            case "세종" -> 36;
            case "경기" -> 41;
            case "강원" -> 42;
            case "충북" -> 43;
            case "충남" -> 44;
            case "전북" -> 45;
            case "전남" -> 46;
            case "경북" -> 47;
            case "경남" -> 48;
            case "제주" -> 50;
            default -> -1;
        };
    }
}

