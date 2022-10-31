package com.comet.auctionfinder.component;

import com.comet.auctionfinder.nearby.model.AuctionSimple;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import java.time.Duration;

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

    // TODO 경매 모델 생성 -> List로 반환할것.
    public String parseData(String province, String city) {
        driver.get(AUCTION_URL);
        String main = driver.getWindowHandle();
        int click = 0;
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.getWindowHandles().forEach((handle) -> closePopup(handle, main));
        driver.switchTo().window(main);
        //프레임 진짜 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 이거 보니까 프레임만 변화되는거 같은데?
        driver.switchTo().frame(driver.findElement(By.name("indexFrame")));
        Select proSelect = new Select(driver.findElement(By.id("idSidoCode1")));
        for (int i = 0; i < proSelect.getOptions().size(); i++) {
            String option = proSelect.getOptions().get(i).getText();
            if (option.equals(province))
                click = i;
        }
        proSelect.selectByIndex(click);
        Select citySelect = new Select(driver.findElement(By.id("idSiguCode1")));
        if (!city.equals("전체")) {
            for (int i = 0; i < citySelect.getOptions().size(); i++) {
                String option = citySelect.getOptions().get(i).getText();
                if (option.equals(city))
                    click = i;
            }
            citySelect.selectByIndex(click);
        }

        //driver.findElement(By.id("main_btn")).click(); //검색창
        driver.executeScript("fastSrch()"); //검색 로드
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver.findElement(By.className("Ltbl_list")).getText();
    }

    //경북 -> 경상북도
    public String matchProvince(String pro) {
        return switch (pro) {
            default -> "서울특별시"; //파싱 안될경우
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

        };
    }

    private void closePopup(String handle, String main) {
        if (!handle.contentEquals(main))
            driver.switchTo()
                    .window(handle)
                    .close();
    }


}
