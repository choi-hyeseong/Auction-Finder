package com.comet.auctionfinder.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class DetailList {

    private String number;
    private String type;
    private String detail;
}
