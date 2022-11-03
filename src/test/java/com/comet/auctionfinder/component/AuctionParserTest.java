package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AuctionParserTest {

    private AuctionParser parser = new AuctionParser(new AuctionCache());

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
}