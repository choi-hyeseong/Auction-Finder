package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Heart;
import com.comet.auctionfinder.model.MemberHeart;
import lombok.Data;

@Data
public class HeartResponseDto {

    public HeartResponseDto(MemberHeart heart) {
        this.court = heart.getHeart().getCourt();
        this.auctionValue = heart.getHeart().getAuctionValue();
    }

    private String court;
    private String auctionValue;

}
