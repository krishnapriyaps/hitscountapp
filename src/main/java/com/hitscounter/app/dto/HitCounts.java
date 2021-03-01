package com.hitscounter.app.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HitCounts hitCounts = (HitCounts) o;
        return url.equals(hitCounts.url) &&
            count.equals(hitCounts.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, count);
    }
}
