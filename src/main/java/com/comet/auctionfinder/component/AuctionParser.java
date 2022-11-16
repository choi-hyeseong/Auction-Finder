package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
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
            wait.until((dri) -> dri.getWindowHandles().size() > 1); //팝업 대기
            for (String handle : driver.getWindowHandles())
                closePopup(driver, handle, main);
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
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(result)));
                NodeList list = doc.getElementsByTagName("option");
                List<String> strList = new ArrayList<>();
                for (int i = 0; i < list.getLength(); i++)
                    strList.add(list.item(i).getChildNodes().item(1).toString());
                strList.remove(strList.size() - 1);
                strList.remove(0);
                //첫번째와 끝값은 쓰레기값
                cities.addAll(strList.stream().distinct().map((data) -> data.replace("[#cdata-section: ", "").replace("]", "").trim()).toList());
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

