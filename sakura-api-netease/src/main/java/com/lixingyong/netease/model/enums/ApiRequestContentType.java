package com.lixingyong.netease.model.enums;

import utils.ValueEnum;

/**
 * 网易云音乐参数请求类型
 *
 * @author LIlGG
 * @since 2021/8/7
 */
public enum ApiRequestContentType implements ValueEnum<String> {
    /**
     * 获取歌单音乐
     */
    PLAYLIST("playlist"),
    /**
     * 获取歌手音乐
     */
    ARTIST("artist"),
    /**
     * 搜索音乐
     */
    SEARCH("search"),
    /**
     * 精品歌单
     */
    HIGH_PLAYLIST("highplaylist"),
    /**
     * 音乐链接
     */
    URL("url"),
    /**
     * 音乐图片
     */
    PIC("pic"),
    /**
     * 歌词
     */
    LRC("lrc");

    /**
     * 请求类型
     */
    final String type;

    ApiRequestContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getValue() {
        return type;
    }
}
