package com.lixingyong.music.server.netease;

import com.lixingyong.music.server.netease.resource.NeteaseNodeJs;
import com.lixingyong.music.server.netease.service.NeteaseApiServiceImpl;
import com.lixingyong.music.service.MusicApiService;
import java.net.http.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.HttpRequestUtil;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
@Configuration
@ConditionalOnProperty(prefix = "api.music.netease", value = "enable", havingValue = "true", matchIfMissing = true)
public class NeteaseHttpConfiguration {

    private final NeteaseApiProperties properties;

    public NeteaseHttpConfiguration(NeteaseApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MusicApiService neteaseApiServiceImpl(NeteaseNodeJs neteaseNodeJs) {
        return new NeteaseApiServiceImpl(neteaseNodeJs);
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
