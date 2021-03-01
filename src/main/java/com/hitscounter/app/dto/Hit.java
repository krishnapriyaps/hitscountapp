package com.hitscounter.app.dto;

public class Hit {
    private String userId;
    private String url;
    private String type;
    private long timestamp;

    private Hit(String userId, String url, String type, long timestamp) {
        this.userId = userId;
        this.url = url;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Hit{" +
            "userId='" + userId + '\'' +
            ", url='" + url + '\'' +
            ", type='" + type + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }

    public static class HitBuilder {
        private String userId;
        private String url;
        private String type;
        private long timestamp;

        private HitBuilder() {
        }

        public static HitBuilder get() {
            return new HitBuilder();
        }

        public HitBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public HitBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public HitBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public HitBuilder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Hit build() {
            return new Hit(userId, url, type, timestamp);
        }
    }
}
