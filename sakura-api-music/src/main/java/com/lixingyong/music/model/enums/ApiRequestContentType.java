package com.lixingyong.music.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 音乐请求时所支持内容类型
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
     * 搜索音乐（默认）
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
