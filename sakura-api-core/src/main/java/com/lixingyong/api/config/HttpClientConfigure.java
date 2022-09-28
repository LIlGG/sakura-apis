package com.lixingyong.api.config;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * 基于 JDK11 的 HttpClient 配置。
 *
 * <p>允许用户使用代理</p>
 *
 * @author LIlGG
 * @since 2022-09-27
 */
@Configurable
@ConditionalOnWebApplication
public class HttpClientConfigure {
    /**
     * HttpClient 默认连接超时时间
     */
    private final static int DEFAULT_CONNECT_TIMEOUT_SECOND = 30;

    @Bean
    @ConditionalOnMissingBean
    public ProxySelector defaultProxySelector() {
        return ProxySelector.getDefault();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClient defaultHttpClient(ProxySelector proxySelector) {
        return HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(DEFAULT_CONNECT_TIMEOUT_SECOND))
            .proxy(proxySelector)
            .build();
    }
}
