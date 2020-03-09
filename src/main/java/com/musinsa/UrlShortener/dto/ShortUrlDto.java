package com.musinsa.UrlShortener.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrlDto {

    @NotBlank(message = "{url.notblank}")
    @URL(message = "{url.invalid}")
    private String url;

    private String shortUrl;

    private long count = 0L;

    @Builder
    public ShortUrlDto(@NotBlank String url) {
        this.url = url;
    }
}
