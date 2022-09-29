package com.lixingyong.netease.model.enums;

import com.lixingyong.netease.resource.model.enums.SearchType;
import utils.ValueEnum;

/**
 * 网易云音乐搜索功能支持的请求参数类型
 *
 * @author LIlGG
 * @since 2021/8/7
 */
public enum SearchParamType implements ValueEnum<String> {
    /**
     * 搜索单曲
     */
    AUDIO("audio", SearchType.SONG),
    /**
     * 搜索歌单
     */
    PLAYLIST("playlist", SearchType.PLAYLIST);

    /**
     * 请求类型
     */
    final String type;

    final SearchType searchType;

    SearchParamType(String type, SearchType searchType) {
        this.type = type;
        this.searchType = searchType;
    }

    public String getType() {
        return type;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    @Override
    public String getValue() {
        return type;
    }
}
