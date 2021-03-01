package com.hitscounter.app.utils;

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtils {

    public static String getUrl(String url) throws URISyntaxException {
        // Uri with space should be escaped
        URI uri = new URI(url.replace(" ", "%20"));
        String path = uri.getPath().equals("/") ? "" : uri.getPath();
        return format("%s%s", uri.getHost(), path);
    }
}
