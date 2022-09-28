package com.lixingyong.netease.resource.model.enums;

import com.lixingyong.netease.resource.model.entity.search.AlbumSearchList;
import com.lixingyong.netease.resource.model.entity.search.ArtistSearchList;
import com.lixingyong.netease.resource.model.entity.search.PlaylistSearchList;
import com.lixingyong.netease.resource.model.entity.search.SearchList;
import com.lixingyong.netease.resource.model.entity.search.SongSearchList;
import utils.ValueEnum;

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
    SONG(1, SongSearchList.class),
    /**
     * 专辑
     */
    ALBUM(10, AlbumSearchList.class),
    /**
     * 歌手
     */
    ARTIST(100, ArtistSearchList.class),
    /**
     * 歌单
     */
    PLAYLIST(1000, PlaylistSearchList.class);

    final int type;
    final Class<? extends SearchList> clazz;

    SearchType(int type, Class<? extends SearchList> searchListClass) {
        this.type = type;
        this.clazz = searchListClass;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public Class<? extends SearchList> getSearchListClass() {
        return clazz;
    }
}
