package com.comet.auctionfinder.nearby.model;

import com.comet.auctionfinder.util.Twin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class AuctionSimple {

    private String court; //할당된 법원
    private String auctionNumber; //할당된 사건번호
    private String type; //물건번호 용도?
    private List<Twin<String, String>> area;
    private String extra; //추가 정보(비고)
    private long checkValue; //감정 평가액
    private long minimumValue; //최저 매각가격
    private String part; //담당계
    private Date until; //매각기한
    private String status; //진행상태 (enum으로 해도될듯)


}
