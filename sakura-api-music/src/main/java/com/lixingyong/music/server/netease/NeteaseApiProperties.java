package com.lixingyong.music.server.netease;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIlGG
 * @since 2022-09-21
 */
@ConfigurationProperties(prefix = "api.music.netease")
public class NeteaseApiProperties {

    /**
     * 网易云音乐 NodeJS 版本地址
     */
    private String upstreamHost;

    public String getUpstreamHost() {
        return upstreamHost;
    }

    public void setUpstreamHost(String upstreamHost) {
        this.upstreamHost = upstreamHost;
    }
}
