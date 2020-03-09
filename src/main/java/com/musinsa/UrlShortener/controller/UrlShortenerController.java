package com.musinsa.UrlShortener.controller;

import com.musinsa.UrlShortener.model.ShortUrl;
import com.musinsa.UrlShortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UrlShortenerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * URL 입력폼 제공 및 결과 출력 Page.
     *
     * @return template name.
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Shortening된 URL을 요청받아 원래 URL로 리다이렉트
     *
     * @param shortKey Shortening된 URL Key
     * @return
     */
    @RequestMapping("/{shortKey:[0-9a-zA-Z]+}")
    private String redirectTo(@PathVariable("shortKey") String shortKey) {
        ShortUrl shortUrl = urlShortenerService.findByShortKey(shortKey);
        return "redirect:" + shortUrl.getUrl();
    }

}
