package com.lixingyong.music.server.netease.resource.model.enums;

import com.lixingyong.music.model.enums.SearchParamType;
import com.lixingyong.music.server.netease.resource.model.entity.search.AlbumSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.ArtistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.PlaylistSearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SearchList;
import com.lixingyong.music.server.netease.resource.model.entity.search.SongSearchList;
import com.lixingyong.music.utils.MusicApiException;
import java.util.stream.Stream;
import com.lixingyong.common.utils.ValueEnum;

/**
 * 搜索功能类型，目前仅支持一部分实现搜索
 *
 * @see <a href="https://binaryify.github.io/NeteaseCloudMusicApi/#/?id=%e6%90%9c%e7%b4%a2">列表搜索</a>
 *
 * @author LIlGG
 * @since 2021/8/3
 */
public enum SearchType implements ValueEnum<Integer> {
    /**
     * 单曲，默认
     */
    SONG(1, SongSearchList.class, SearchParamType.AUDIO),
    /**
     * 专辑
     */
    ALBUM(10, AlbumSearchList.class, null),
    /**
     * 歌手
     */
    ARTIST(100, ArtistSearchList.class, null),
    /**
     * 歌单
     */
    PLAYLIST(1000, PlaylistSearchList.class, SearchParamType.PLAYLIST);

    final int type;
    final Class<? extends SearchList> clazz;
    final SearchParamType searchParamType;

    SearchType(int type, Class<? extends SearchList> searchListClass,
               SearchParamType searchParamType) {
        this.type = type;
        this.clazz = searchListClass;
        this.searchParamType = searchParamType;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public Class<? extends SearchList> getSearchListClass() {
        return clazz;
    }

    public SearchParamType getSearchParamType() {
        return searchParamType;
    }

    public static SearchType getValueByParamType(SearchParamType searchParamType) {
        return Stream.of(SearchType.values())
            .filter(item -> item.getSearchParamType().equals(searchParamType))
            .findFirst()
            .orElseThrow(() -> new MusicApiException("不存在当前查询类型，请更换查询条件"));
    }
}
