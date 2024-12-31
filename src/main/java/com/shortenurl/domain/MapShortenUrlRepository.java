package com.shortenurl.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository{

    private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();


    @Override
    public void saveShortenUrl(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }

    @Override
    public ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey) {
        return shortenUrls.get(shortenUrlKey);
    }

    @Override
    public List<ShortenUrl> findAll() {
        return new ArrayList<>(shortenUrls.values());
    }
}
