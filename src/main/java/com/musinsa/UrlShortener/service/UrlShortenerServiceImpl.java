package com.musinsa.UrlShortener.service;

import com.musinsa.UrlShortener.exception.ShortUrlNotFoundException;
import com.musinsa.UrlShortener.model.ShortUrl;
import com.musinsa.UrlShortener.repository.ShortUrlRepository;
import com.musinsa.UrlShortener.util.BaseN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    private ShortUrlRepository shortUrlRepository;

    public UrlShortenerServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    /**
     * url 을 입력 받아 key 생성.
     * <p>동일 url 에 대해 동일한 key 생성을 위해, url 의 md5 hash 값용 이용하여 중복 검사를 수행 한다.</p>
     *
     * @param url 원본 URL
     * @return shortened url entity
     */
    @Override
    public ShortUrl makeShortUrl(String url) {

        ShortUrl sourceUrl = ShortUrl.builder().url(url).build();
        ShortUrl shortUrl  = shortUrlRepository.findByHash(sourceUrl.getHash());

        shortUrl = Optional.ofNullable(shortUrl).orElseGet(() -> shortUrlRepository.save(sourceUrl));
        if (!Optional.of(shortUrl).map(ShortUrl::getShortKey).isPresent()) {
            shortUrl.setShortKey(BaseN.encode(shortUrl.getId(), BaseN.BASE52));
            shortUrlRepository.save(shortUrl);
        }

        return shortUrl;
    }

    /**
     * short key 를 입력 받아 원본 url을 검색하고, 조회수를 증가 한다.
     *
     * @param shortKey 원본 url 에 대한 short key
     * @return 원본 url 정보를 담고 있는 entity
     * @throws ShortUrlNotFoundException 원본 url 을 찾을 수 없을 경우 발생
     */
    @Override
    public ShortUrl findByShortKey(String shortKey) {
        Optional<ShortUrl> shortUrl = Optional.ofNullable(shortUrlRepository.findByShortKey(shortKey));
        shortUrl.orElseThrow(ShortUrlNotFoundException::new);
        shortUrlRepository.increaseCount(shortUrl.get().getId());
        return shortUrl.get();
    }

}
