package com.musinsa.UrlShortener.dto;

import com.musinsa.UrlShortener.model.ShortUrl;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    /**
     * TODO: 현재 실행 중인 host 추출
     */
    private final String BASE_URL = "http://localhost:8080";

    private ModelMapper modelMapper;

    /**
     * shortKey 값으로 shortened url 생성
     */
    private Converter<String, String> shortKeyToUrl = new AbstractConverter<String, String>() {
        @Override
        protected String convert(String source) {
            return source == null ? null : BASE_URL + "/" + source;
        }
    };

    /**
     * ShortUrl Entity -> ShortUrlDto 변환 맵
     */
    private PropertyMap<ShortUrl, ShortUrlDto> shortUrlMap = new PropertyMap<ShortUrl, ShortUrlDto>() {
        @Override
        protected void configure() {
            using(shortKeyToUrl).map().setShortUrl(source.getShortKey());
        }
    };

    public ShortUrlMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(shortUrlMap);
    }

    public ShortUrlDto toDto(ShortUrl shortUrl) {
        return modelMapper.map(shortUrl, ShortUrlDto.class);
    }

    public ShortUrl toEntity(ShortUrlDto shortUrlDto) {
        return ShortUrl.builder()
                .url(shortUrlDto.getUrl())
                .build();
    }

}
