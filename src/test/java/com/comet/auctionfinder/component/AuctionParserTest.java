package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class AuctionParserTest {

    private AuctionParser parser = new AuctionParser(new AuctionCache());

    static {
        WebDriverManager.chromedriver()
                .setup(); //크롬드라이버 셋업
    }
    @Test
    @DisplayName("특정지역 경매목록 파싱 테스트")
    public void PARSE_TEST() throws Exception {
        String pro = parser.matchProvince("경북"); //올바르게 매칭
        String city = "김천시";
        Twin<AuctionResponse, List<AuctionSimple>> result = parser.parseData(pro, city).get();
        assertEquals(result.getFirst(), AuctionResponse.FOUND);
    }

    @Test
    @DisplayName("전체 지역 경매목록 파싱 테스트")
    public void PARSE_ALL_TEST() throws Exception {
        String pro = parser.matchProvince("경북"); //올바르게 매칭
        String city = "전체";
        System.out.println(parser.parseData(pro, city));
    }

    @Test
    @DisplayName("지역 파싱 테스트")
    public void CITY_PARSE_TEST() {
        try {
            List<String> list = parser.getCities("경북").get();
            for (int i = 0; i < list.size(); i++){
                System.out.println(i + " : " + list.get(i));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("디테일 파싱 테스트")
    public void DETAIL_PARSE_TEST() {
        parser.parseDetail("김천지원", "2021타경120");
    }
}