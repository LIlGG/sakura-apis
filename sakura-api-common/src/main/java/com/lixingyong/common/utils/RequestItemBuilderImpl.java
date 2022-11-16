package com.lixingyong.common.utils;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
class RequestItemBuilderImpl implements RequestItem.Builder {

    URI uri;
    String method;
    Map<String, String> headers;
    Map<String, Object> formMap;
    Duration duration;

    public RequestItemBuilderImpl() {
        this.headers = new HashMap<>();
        this.formMap = new HashMap<>();
        this.method = "GET"; // 默认为 GET 请求
    }

    public RequestItemBuilderImpl(URI uri) {
        this.headers = new HashMap<>();
        this.formMap = new HashMap<>();
        this.uri = uri;
        this.method = "GET"; // 默认为 GET 请求
    }

    @Override
    public RequestItem.Builder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public RequestItem.Builder header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    @Override
    public RequestItem.Builder header(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    @Override
    public RequestItem.Builder form(String key, Object value) {
        this.formMap.put(key, value);
        return this;
    }

    @Override
    public RequestItem.Builder form(Map<String, Object> formMap) {
        this.formMap.putAll(formMap);
        return this;
    }

    @Override
    public RequestItem.Builder method(String method) {
        this.method = method;
        return this;
    }

    @Override
    public RequestItem.Builder timeout(Duration duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public RequestItem build() {
        return new RequestItemImpl(this);
    }
}
