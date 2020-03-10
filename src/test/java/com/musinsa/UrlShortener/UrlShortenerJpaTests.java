package com.musinsa.UrlShortener;

import com.musinsa.UrlShortener.model.ShortUrl;
import com.musinsa.UrlShortener.repository.ShortUrlRepository;
import com.musinsa.UrlShortener.util.Md5Hash;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2. JPA CRUD 테스트")
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:jdbc.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UrlShortenerJpaTests {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerJpaTests.class);

    private final String testUrl = "https://test.musinsa.com/app/product/detail/677129/0";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    @Order(1)
    @DisplayName("1. ShortUrl Entity Build 시 source url 에 대한 hash 생성 테스트")
    public void buildSortUrlTest() {

        final ShortUrl sourceUrl = ShortUrl.builder().url(testUrl).build();
        final String   hash      = Md5Hash.md5Hex(testUrl);

        assertThat(hash).isEqualTo(sourceUrl.getHash());
    }

    @Test
    @Order(2)
    @DisplayName("2. ShortUrl Entity insert 테스트")
    public void sortUrlInsertTest() {

        final String   targetUrl = testUrl + Math.random();
        final ShortUrl sourceUrl = ShortUrl.builder().url(targetUrl).build();
        final ShortUrl actualUrl = shortUrlRepository.save(sourceUrl);

        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.getUrl()).isEqualTo(sourceUrl.getUrl());
    }

    @Test
    @Order(3)
    @DisplayName("3. ShortUrl Entity select 테스트")
    public void sortUrlSelectTest() {

        final String   targetUrl = testUrl + Math.random();
        final ShortUrl sourceUrl = ShortUrl.builder().url(targetUrl).build();
        entityManager.persist(sourceUrl);
        entityManager.clear();

        final ShortUrl actualUrl = shortUrlRepository.getOne(sourceUrl.getId());

        assertThat(actualUrl.getUrl()).isEqualTo(sourceUrl.getUrl());
        assertThat(actualUrl.getHash()).isEqualTo(sourceUrl.getHash());
    }

    @Test
    @Order(4)
    @DisplayName("4. ShortUrl Repository findByHash 테스트")
    public void sortUrlfindByHashTest() {

        final String   targetUrl = testUrl + Math.random();
        final ShortUrl sourceUrl = ShortUrl.builder().url(targetUrl).build();
        entityManager.persist(sourceUrl);
        entityManager.clear();

        final ShortUrl actualUrl = shortUrlRepository.findByHash(sourceUrl.getHash());

        assertThat(actualUrl.getUrl()).isEqualTo(sourceUrl.getUrl());
        assertThat(actualUrl.getHash()).isEqualTo(sourceUrl.getHash());
    }

    @Test
    @Order(5)
    @DisplayName("5. ShortUrl Repository findByShortKey 테스트")
    public void sortUrlfindByShortKeyTest() {

        final String   targetUrl = testUrl + Math.random();
        final ShortUrl sourceUrl = ShortUrl.builder().url(targetUrl).build();
        sourceUrl.setShortKey("AABB");
        entityManager.persist(sourceUrl);
        entityManager.clear();

        final ShortUrl actualUrl = shortUrlRepository.findByShortKey(sourceUrl.getShortKey());

        assertThat(actualUrl.getUrl()).isEqualTo(sourceUrl.getUrl());
        assertThat(actualUrl.getHash()).isEqualTo(sourceUrl.getHash());
    }

    @Test
    @Order(6)
    @DisplayName("6. ShortUrl Repository increaseCount 테스트")
    public void sortUrlincreaseCountTest() {

        final String   targetUrl = testUrl + Math.random();
        final ShortUrl sourceUrl = ShortUrl.builder().url(targetUrl).build();
        entityManager.persist(sourceUrl);
        entityManager.clear();

        shortUrlRepository.increaseCount(sourceUrl.getId());
        final ShortUrl actualUrl = shortUrlRepository.getOne(sourceUrl.getId());

        assertThat(actualUrl.getCount()).isEqualTo(sourceUrl.getCount() + 1);
    }

}
