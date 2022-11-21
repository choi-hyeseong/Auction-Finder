package com.comet.auctionfinder.model;

import com.comet.auctionfinder.util.DetailDate;
import com.comet.auctionfinder.util.DetailList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuctionDetail {

    private String auctionValue; //사건번호
    private String auctionNumber; //물건번호
    private String type; //물건 종류
    private String checkValue; //감정 평가액
    private String minimumValue; //최저 매각가격
    private String auctionType; //입찰 방법
    private String endDate; //매각 기일
    private String extra; //물건 비고
    private List<String> areas; //지역
    private String part; //담당계
    private String startDate;
    private String auctionDate;
    private String bidDate;
    private String askBid; //청구 금액
    private String imageExtra; //전경도 수
    private List<String> images;
    private List<DetailDate> dates;
    private List<DetailList> list; //목록내역
    private String summary;



}
