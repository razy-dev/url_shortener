package com.musinsa.UrlShortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.UrlShortener.dto.ShortUrlDto;
import com.musinsa.UrlShortener.exception.ShortUrlNotFoundException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlShortenerApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerApplicationTests.class);

    private final String apiUrl    = "/api/urls/shorten";
    private final String sourceUrl = "https://test.musinsa.com/app/product/detail/677129/0";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    @DisplayName("1. Short URL 생성 Rest Api 테스트")
    public void makeShortUrlTest() throws Exception {

        // given
        final ShortUrlDto shortUrlDto = ShortUrlDto.builder().url(sourceUrl).build();
        final String      requestData = mapper.writeValueAsString(shortUrlDto);

        // when
        final ResultActions actions = mvc.perform(post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData)
        ).andDo(print());

        // then
        actions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.url").value(sourceUrl))
                .andExpect(jsonPath("$.shortUrl").isNotEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("2. Short Key 로 원본 URL Redirect 테스트")
    public void redirectToTest() throws Exception {

        // given
        final ShortUrlDto shortUrlDto = ShortUrlDto.builder().url(sourceUrl).build();
        final String      requestData = mapper.writeValueAsString(shortUrlDto);

        final MvcResult result = mvc.perform(post(apiUrl)
                .content(requestData)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final ShortUrlDto responseDto = mapper.readValue(result.getResponse().getContentAsString(), ShortUrlDto.class);

        // when
        final ResultActions actions = mvc.perform(get(responseDto.getShortUrl()));

        // then
        actions
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(sourceUrl));
    }

    @Test
    @Order(3)
    @DisplayName("3. Short Key 로 원본 URL 을 찾지 못할 경우 테스트")
    public void notFoundUrlTest() throws Exception {

        // given
        final String abnormalShortenUrl = "/FFFFFFFFF";

        // when
        final ResultActions actions = mvc.perform(get(abnormalShortenUrl));

        // then
        actions
                .andExpect(status().is4xxClientError())
                .andExpect(
                        (result) -> assertTrue(result
                                .getResolvedException()
                                .getClass()
                                .isAssignableFrom(ShortUrlNotFoundException.class))
                );
    }

}
