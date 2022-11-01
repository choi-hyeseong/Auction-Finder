package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Component
public class AuctionParser {

    private final ChromeDriver driver;
    private static final String AUCTION_URL = "https://www.courtauction.go.kr/";

    public AuctionParser() {
        WebDriverManager.chromedriver()
                .setup(); //크롬드라이버 셋업
        ChromeOptions options = new ChromeOptions();
        options
                .addArguments("--disable-gpu")
                .addArguments("--disable-logging")
                .addArguments("headless")
                .addArguments("--disable-popup-blocking")
                .addArguments("--blink-settings=imagesEnabled=false");
        driver = new ChromeDriver(options);
    }

    // TODO 여러페이지 파싱할것
    public Twin<AuctionResponse, List<AuctionSimple>> parseData(String province, String city) throws Exception {
        List<AuctionSimple> result = new ArrayList<>();
        //load logic
        driver.get(AUCTION_URL);
        String main = driver.getWindowHandle();
        int click = 0;
        Thread.sleep(2000); //팝업 대기
        driver.getWindowHandles().forEach((handle) -> closePopup(handle, main));
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
                return Twin.of(AuctionResponse.CITY_NOT_FOUND, result);
            }
            citySelect.selectByIndex(click);
        }

        //driver.findElement(By.id("main_btn")).click(); //검색창
        driver.executeScript("fastSrch()"); //검색 로드
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //검색 결과창
        new Select(driver.findElement(By.id("ipage"))).selectByIndex(3); //40페이지씩 로드
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //로드 대기


        //parse logic
        List<WebElement> elements = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div[3]/div[4]/form[1]/table/tbody")); //해당 페이지 경매품목
        for (WebElement element : elements) {
            List<WebElement> trList = element.findElements(By.tagName("tr")); //한 행
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
                Date until = new SimpleDateFormat("yyyy.mm.dd").parse(lastValue[1]);
                String status = lastValue[2];
                result.add(AuctionSimple.builder().court(court).auctionNumber(auctionNumber).type(type).area(area).extra(extra)
                        .checkValue(checkValue).minimumValue(minimumValue).part(part).until(until).status(status)
                        .build());
            }
        }
        //driver.close(); //끄지마... => 창이 하나만 남아있을경우 셀레니움 전체를 종료하는 quit이랑 동일하게 수행됨..
        return Twin.of(AuctionResponse.FOUND, result);
    }

    //경북 -> 경상북도
    //default가 맨위에 있으면 얘 먼저 파싱됨..
    public String matchProvince(String pro) {
        pro = pro.trim(); //띄어쓰기 하나때문에 ㅎㅎ....
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

    private void closePopup(String handle, String main) {
        if (!handle.contentEquals(main))
            driver.switchTo()
                    .window(handle)
                    .close();
    }

    public void quitSelenium() {
        driver.quit();
    }


}
