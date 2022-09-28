package com.lixingyong.netease;

import com.lixingyong.netease.resource.NeteaseNodeJs;
import java.net.http.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import utils.HttpRequestUtil;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
public class NeteaseHttpConfiguration {

    private final NeteaseApiProperties properties;

    public NeteaseHttpConfiguration(NeteaseApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnClass(NeteaseNodeJs.class)
    public NeteaseNodeJs neteaseNodeJs(HttpRequestUtil httpRequestUtil) {
        String upstreamHost = this.properties.getUpstreamHost();
        NeteaseNodeJs neteaseNodeJs = new NeteaseNodeJs(upstreamHost.replaceFirst("/$", ""));
        neteaseNodeJs.setHttpClient(httpRequestUtil);
        return neteaseNodeJs;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(HttpClient.class)
    public HttpRequestUtil neteaseHttpRequest(HttpClient httpClient) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.setHttpClient(httpClient);
        return httpRequestUtil;
    }
}
