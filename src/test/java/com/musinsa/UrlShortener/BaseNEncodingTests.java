package com.musinsa.UrlShortener;

import com.musinsa.UrlShortener.exception.OutOfMaxLengthException;
import com.musinsa.UrlShortener.util.BaseN;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseNEncodingTests {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerJpaTests.class);

    @Test
    @Order(1)
    @DisplayName("1. Base N encoding 테스트")
    public void BaseNEncodingTest() {

        final long   sourceNum  = (long) (Math.random() * Integer.MAX_VALUE);
        final String encodedStr = BaseN.encode(sourceNum, BaseN.BASE52);

        assertThat(encodedStr).isEqualTo(BaseN.encode(sourceNum, BaseN.BASE52));
    }

    @Test
    @Order(2)
    @DisplayName("2. Base N decoding 테스트")
    public void BaseNDecodingTest() {
        // TODO:
    }

    @Test
    @Order(3)
    @DisplayName("3. Base N encoding 최대 길이 테스트")
    public void MaxLengthExceptionTest() {

        assertThrows(OutOfMaxLengthException.class, () -> {
            BaseN.encode((long) Math.pow(52, BaseN.MAX_LENGTH) + 1, BaseN.BASE52);
        });
    }

}
