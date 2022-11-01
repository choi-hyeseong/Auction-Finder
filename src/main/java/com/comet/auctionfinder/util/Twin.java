package com.comet.auctionfinder.util;

import lombok.AllArgsConstructor;
import lombok.Data;

//연관 관계에 있는 데이터를 묶기 위한 클래스
@Data
@AllArgsConstructor
public class Twin<T, V> {

    private T first;
    private V second;

    private Twin() {
        //of 쓰세용
    }

    public static <F, S> Twin<F, S> of(F first, S second) {
        return new Twin<>(first, second);
    }

}
