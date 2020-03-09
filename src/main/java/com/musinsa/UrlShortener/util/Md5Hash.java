package com.musinsa.UrlShortener.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

/**
 * @author razy.kim
 */
public class Md5Hash {

    public static String md5Hex(String str) {
        return DigestUtils.md5Hex(Optional.ofNullable(str).get().trim().toLowerCase());
    }
}
