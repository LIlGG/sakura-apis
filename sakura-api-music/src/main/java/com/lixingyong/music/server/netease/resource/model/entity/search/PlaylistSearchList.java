package com.lixingyong.music.server.netease.resource.model.entity.search;

import com.lixingyong.music.server.netease.resource.model.entity.playlist.PlayList;
import java.util.List;
import lombok.Data;

/**
 * 歌单搜索列表
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class PlaylistSearchList implements SearchList {
    /**
     * 本页歌单列表
     */
    private List<PlayList> playlists;
    /**
     * 搜索到的歌单总数
     */
    private int playlistCount;
}
