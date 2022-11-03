package com.comet.auctionfinder.component;

import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.model.CachedValue;
import com.comet.auctionfinder.util.Twin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuctionCache {

    private final Map<Twin<String, String>, CachedValue<List<AuctionSimple>>> cachedSimple = new ConcurrentHashMap<>(); //pro, city, cache
    @Value("${cache.expire}")
    private long expired;
    public boolean isCacheExist(Twin<String, String> key) {
        return cachedSimple.containsKey(key) && !cachedSimple.get(key).isExpired();
    }

    public Optional<List<AuctionSimple>> getCache(Twin<String, String> key) {
        return cachedSimple.containsKey(key) ? Optional.of(cachedSimple.get(key).getItem()) : Optional.empty();
    }

    public void putCache(Twin<String, String> key, List<AuctionSimple> simple) {
        cachedSimple.put(key, new CachedValue<>(simple, expired, System.currentTimeMillis()));
    }

}
