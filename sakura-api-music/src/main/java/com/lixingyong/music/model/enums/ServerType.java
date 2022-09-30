package com.lixingyong.music.model.enums;

import com.lixingyong.music.server.netease.service.NeteaseApiServiceImpl;
import com.lixingyong.music.service.MusicApiService;
import utils.ValueEnum;

/**
 * 支持的服务类型
 *
 * @author LIlGG
 * @since 2022-09-30
 */
public enum ServerType implements ValueEnum<String>  {

    NETEASE("netease", NeteaseApiServiceImpl.class);

    /**
     * 服务请求名
     */
    private final String serverName;

    /**
     * 服务类实现类
     */
    private final Class<? extends MusicApiService> serverClass;

    ServerType(String serverName, Class<? extends MusicApiService> serverClass) {
        this.serverName = serverName;
        this.serverClass = serverClass;
    }

    @Override
    public String getValue() {
        return serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public Class<? extends MusicApiService> getServerClass() {
        return serverClass;
    }
}
