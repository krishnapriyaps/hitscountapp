package com.hitscounter.app.utils;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import org.junit.Test;

public class UrlUtilsTest{

    @Test
    public void testGetUrl() throws URISyntaxException {
        String url = "http://boot.example.com/bee/berry";
        assertEquals("boot.example.com/bee/berry", UrlUtils.getUrl(url));
    }

    @Test
    public void testGetUrlWithRootPath() throws URISyntaxException {
        String url = "http://example.com/";
        assertEquals("example.com", UrlUtils.getUrl(url));
    }

    @Test
    public void testGetUrlWithSpace() throws URISyntaxException {
        String url = "http://www.someamazingwebsite.com/county-2014/engine/match/69272 1.html?cluster=undefined;view=comparison;wrappertype=none#live";
        assertEquals("www.someamazingwebsite.com/county-2014/engine/match/69272 1.html",
            UrlUtils.getUrl(url));
    }
}
