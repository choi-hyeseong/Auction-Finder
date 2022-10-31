package com.comet.auctionfinder.util;

import lombok.Data;

//연관 관계에 있는 데이터를 묶기 위한 클래스
@Data
public class Twin<T, V> {

    private T first;
    private V second;

}
