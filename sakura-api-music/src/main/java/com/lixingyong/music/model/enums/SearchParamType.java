package com.lixingyong.music.model.enums;

import utils.ValueEnum;

/**
 * 搜索功能所支持的类型
 *
 * @author LIlGG
 * @since 2021/8/7
 */
public enum SearchParamType implements ValueEnum<String> {
    /**
     * 搜索单曲
     */
    AUDIO("audio"),
    /**
     * 搜索歌单
     */
    PLAYLIST("playlist");

    /**
     * 请求类型
     */
    final String type;

    SearchParamType(String type) {
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
