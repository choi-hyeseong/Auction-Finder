package com.comet.auctionfinder.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DetailDate {

    private String date;
    private String type;
    private String location;
    private String minimum;
    private String result;

}
