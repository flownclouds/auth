package com.andlinks.utils;

import javax.servlet.http.Cookie;

/**
 * Created by 王凯斌 on 2017/4/26.
 */
public class HttpUtils {

    public static Cookie getCookie(String name, String value, int expiry, String path, boolean isSecure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath(path);
        cookie.setSecure(isSecure);
        return cookie;
    }
}
