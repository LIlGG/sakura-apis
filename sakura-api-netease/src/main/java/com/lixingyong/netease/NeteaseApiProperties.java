package com.lixingyong.netease;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIlGG
 * @since 2022-09-21
 */
@ConfigurationProperties(prefix = "api.netease")
public class NeteaseApiProperties {

    /**
     * 网易云音乐 NodeJS 版本地址
     */
    private String neteaseCloudMusicApiHost;

    public String getNeteaseCloudMusicApiHost() {
        return neteaseCloudMusicApiHost;
    }

    public void setNeteaseCloudMusicApiHost(String neteaseCloudMusicApiHost) {
        this.neteaseCloudMusicApiHost = neteaseCloudMusicApiHost;
    }
}
