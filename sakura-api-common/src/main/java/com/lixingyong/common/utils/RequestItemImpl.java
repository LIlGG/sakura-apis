package com.lixingyong.common.utils;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
class RequestItemImpl extends RequestItem {

    private final URI uri;

    private final String method;

    private final Map<String, String> headers;

    private final Map<String, Object> form;

    private final Duration duration;

    public RequestItemImpl(RequestItemBuilderImpl requestItemBuilder) {
        this.uri = requestItemBuilder.uri;
        this.method = requestItemBuilder.method;
        this.headers = requestItemBuilder.headers;
        this.form = requestItemBuilder.formMap;
        this.duration = requestItemBuilder.duration;
    }

    @Override
    public URI uri() {
        return this.uri;
    }

    @Override
    public String method() {
        return this.method;
    }

    @Override
    public Map<String, String> headers() {
        return this.headers;
    }

    @Override
    public Map<String, Object> formMap() {
        return this.form;
    }

    @Override
    public Duration duration() {
        return this.duration;
    }
}
