package com.comet.auctionfinder.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuctionParserTest {

    private AuctionParser parser = new AuctionParser();

    @Test
    @DisplayName("특정지역 경매목록 파싱 테스트")
    public void PARSE_TEST() throws InterruptedException {
        String pro = parser.matchProvince("서울"); //올바르게 매칭
        String city = "전체";
        System.out.println(parser.parseData(pro, city));
    }

    @Test
    @DisplayName("전체 지역 경매목록 파싱 테스트")
    public void PARSE_ALL_TEST() throws InterruptedException {
        String pro = parser.matchProvince("경북"); //올바르게 매칭
        String city = "전체";
        System.out.println(parser.parseData(pro, city));
    }
}