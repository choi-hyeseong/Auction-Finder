package com.comet.auctionfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
public class CachedValue<T> {
    //크롤링 데이터 캐싱
    private T item;
    private long expire; //@value는 component등으로 빈 탑재되야 사용가능
    private long lastSavedTime;


    public boolean isExpired() {
        return System.currentTimeMillis() - lastSavedTime >= expire;
    }

}
