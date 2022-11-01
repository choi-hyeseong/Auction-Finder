package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class AuctionParserTest {

    private AuctionParser parser = new AuctionParser();

    @Test
    @DisplayName("특정지역 경매목록 파싱 테스트")
    public void PARSE_TEST() throws Exception {
        String pro = parser.matchProvince("서울"); //올바르게 매칭
        String city = "전체";
        Twin<AuctionResponse, List<AuctionSimple>> result = parser.parseData(pro, city);
        System.out.println(result.getFirst());
        result.getSecond().forEach(System.out::println);
    }

    @Test
    @DisplayName("전체 지역 경매목록 파싱 테스트")
    public void PARSE_ALL_TEST() throws Exception {
        String pro = parser.matchProvince("경북"); //올바르게 매칭
        String city = "전체";
        System.out.println(parser.parseData(pro, city));
    }
}