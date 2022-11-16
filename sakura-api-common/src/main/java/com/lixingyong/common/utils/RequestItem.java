package com.lixingyong.common.utils;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
public abstract class RequestItem {

    protected RequestItem() {}

    /**
     * HttpClient 生成器
     *
     * <p>HttpRequestUtil 将负责大部分 HttpRequest 公共参数的构建。
     * 其余参数由该生成器负责传入</p>
     */
    public interface Builder {
        /**
         * 设置 {@code HttpRequest} 的 {@code URI}.
         *
         * @param uri 当前请求的 URI
         * @return 当前 Builder
         */
        Builder uri(URI uri);

        /**
         * 设置 {@code HttpRequest} 的 Header
         * @param name Header key
         * @param value Header value
         * @return 当前 Builder
         *
         * 当 header 存在，则会替换
         */
        Builder header(String name, String value);

        /**
         * 批量设置 {@code HttpRequest} 的 Header
         * @param headers Headers
         * @return 当前 Builder
         *
         * 当 header 存在，则会替换
         */
        Builder header(Map<String, String> headers);

        /**
         * 表单数据
         *
         * @param key 键
         * @param value 值
         * @return 当前 Builder
         */
        Builder form(String key, Object value);

        /**
         * 设置 Map 类型表单数据
         *
         * @param formMap 表单内容
         * @return 当前 Builder
         */
        Builder form(Map<String, Object> formMap);

        /**
         * 请求类型
         *
         * @param method 请求类型
         * @return 当前 Builder
         */
        Builder method(String method);

        /**
         * Http 超时时间
         *
         * @param duration 超时时间
         * @return 当前 Builder
         */
        Builder timeout(Duration duration);

        /**
         * 构建 @{code RequestItem}
         *
         * @return 返回 RequestItem
         */
        RequestItem build();
    }

    public static Builder newBuilder() {
        return new RequestItemBuilderImpl();
    }

    public static Builder newBuilder(URI uri) {
        return new RequestItemBuilderImpl(uri);
    }

    public static Builder newBuilder(String uri) {
        return new RequestItemBuilderImpl(URI.create(uri));
    }

    public abstract URI uri();

    public abstract String method();

    public abstract Map<String, String> headers();

    public abstract Map<String, Object> formMap();

    public abstract Duration duration();

}
