package com.musinsa.UrlShortener.rest;

import com.musinsa.UrlShortener.dto.ShortUrlDto;
import com.musinsa.UrlShortener.dto.ShortUrlMapper;
import com.musinsa.UrlShortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerRestController {

    private UrlShortenerService urlShortenerService;

    private ShortUrlMapper shortUrlMapper;

    public UrlShortenerRestController(UrlShortenerService urlShortenerService, ShortUrlMapper shortUrlMapper) {
        this.urlShortenerService = urlShortenerService;
        this.shortUrlMapper      = shortUrlMapper;
    }

    /**
     * 원본 URL을 Json 형식으로 입력 받아, shortened url 생성 및 반환.
     *
     * @param data
     * @return
     */
    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlDto> shorten(@RequestBody @Valid ShortUrlDto data) {
        return new ResponseEntity<>(
                shortUrlMapper.toDto(urlShortenerService.makeShortUrl(data.getUrl())),
                HttpStatus.CREATED
        );
    }

}
