package com.musinsa.UrlShortener;

import com.musinsa.UrlShortener.model.ShortUrl;
import com.musinsa.UrlShortener.service.UrlShortenerService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:jdbc-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UrlShortenerServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceTests.class);

    private final String testUrl = "https://test.musinsa.com/app/product/detail/677129/0";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    @Order(1)
    @DisplayName("1. source url 에 대한 short Key 생성 테스트")
    public void makeShortKeyTest() {

        final String   url       = testUrl + Math.random();
        final ShortUrl sourceUrl = urlShortenerService.makeShortUrl(url);
        entityManager.flush();

        assertThat(sourceUrl.getShortKey()).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("2. source url 에 대한 동일한 short Key 생성 테스트")
    public void confirmShortKeyTest() {

        final String   url       = testUrl + Math.random();
        final ShortUrl sourceUrl = urlShortenerService.makeShortUrl(url);
        entityManager.flush();

        final ShortUrl actualUrl = urlShortenerService.makeShortUrl(url);
        entityManager.flush();

        assertThat(actualUrl.getShortKey()).isEqualTo(sourceUrl.getShortKey());
    }

    @Test
    @Order(3)
    @DisplayName("3. short Key 로 찾은 url 검증 테스트")
    public void findByShortKeyTest() {

        final ShortUrl sourceUrl = urlShortenerService.makeShortUrl(testUrl);
        entityManager.flush();
        entityManager.clear();

        final ShortUrl actualUrl = urlShortenerService.findByShortKey(sourceUrl.getShortKey());

        assertThat(actualUrl.getUrl()).isEqualTo(testUrl);
    }

}
