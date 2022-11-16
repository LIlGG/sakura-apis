package com.lixingyong.music.server.netease;

import com.lixingyong.common.autoconfigure.channel.ChannelApplication;
import com.lixingyong.music.server.netease.resource.NeteaseNodeJs;
import com.lixingyong.music.server.netease.service.NeteaseApiServiceImpl;
import com.lixingyong.music.service.MusicApiService;
import java.net.http.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.lixingyong.common.utils.HttpRequestUtil;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
@ChannelApplication(value = "Netease", primary = true)
@ConditionalOnProperty(
    prefix = "api.music.netease", value = "enable", havingValue = "true", matchIfMissing = true
)
@EnableConfigurationProperties(NeteaseApiProperties.class)
public class NeteaseHttpConfiguration {
    @Bean
    public MusicApiService neteaseApiServiceImpl(NeteaseNodeJs neteaseNodeJs) {
        return new NeteaseApiServiceImpl(neteaseNodeJs);
    }

    @Bean
    @ConditionalOnClass(NeteaseNodeJs.class)
    public NeteaseNodeJs neteaseNodeJs(HttpRequestUtil httpRequestUtil, NeteaseApiProperties properties) {
        String upstreamHost = properties.getUpstreamHost();
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
