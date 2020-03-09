package com.musinsa.UrlShortener.util;

import com.musinsa.UrlShortener.exception.OutOfMaxLengthException;

public class BaseN {

    public static final int    MAX_LENGTH = 8;
    public static final String BASE52     = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String BASE62     = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 기본 생성자. Base62 방식 encoding
     *
     * @param num encoding 대상 숫자
     * @return
     */
    public static String encode(long num) {
        return encode(num, BASE62);
    }

    /**
     * 지정된 문자열 기반으로 변환하는 생성자
     *
     * @param num      encoding 대상 숫자
     * @param alphabet encoding 기준 문자열
     * @return
     */
    public static String encode(long num, final String alphabet) {

        if (num < 0) throw new IllegalArgumentException("The num value must be greater than or equal to 0");
        if (alphabet == null) throw new IllegalArgumentException("The alphabet must be String");

        final StringBuilder sb      = new StringBuilder();
        final int           base    = alphabet.length();
        final int           minimum = base * base * base; // encoding 결과를 최소 3자리 이상으로 만들기 위한 보정값.

        num = minimum + num;
        do {
            sb.append(alphabet.charAt((int) (num % base)));
            num /= base;
        } while (num > 0);

        if (sb.length() > MAX_LENGTH) throw new OutOfMaxLengthException("Encode result out of range");

        return sb.toString();

    }

    /**
     * TODO: decoding 기능 작성
     *
     * @param str decoding 대상 문자열
     * @return
     */
    public static long decode(String str) {
        return decode(str, BASE62);
    }

    /**
     * TODO: decoding 기능 작성
     *
     * @param str      decoding 대상 문자열
     * @param alphabet decoding 기준 문자열
     * @return
     */
    public static long decode(String str, final String alphabet) {
        return 0L;
    }
}
