package com.hitscounter.app.dto;

public class HitCounts {
    private String url;
    private String count;

    public HitCounts(String url, String count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public String getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "HitCounts{" +
            "url='" + url + '\'' +
            ", count='" + count + '\'' +
            '}';
    }
}
