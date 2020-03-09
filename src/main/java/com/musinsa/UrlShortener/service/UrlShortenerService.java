package com.musinsa.UrlShortener.service;

import com.musinsa.UrlShortener.model.ShortUrl;

public interface UrlShortenerService {

    public ShortUrl makeShortUrl(String url);

    public ShortUrl findByShortKey(String shortKey);

}
