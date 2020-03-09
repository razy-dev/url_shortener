package com.musinsa.UrlShortener.repository;

import com.musinsa.UrlShortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByHash(String hash);

    ShortUrl findByShortKey(String shortKey);

    /**
     * TODO: native query 를 사용하지 않을 방법은?
     *
     * @param id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE short_url SET count = count + 1, updated_at = CURRENT_TIMESTAMP() WHERE id = :id", nativeQuery = true)
    void increaseCount(@Param("id") long id);
}
