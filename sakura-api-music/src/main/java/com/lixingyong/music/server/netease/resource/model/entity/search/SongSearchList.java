package com.lixingyong.music.server.netease.resource.model.entity.search;

import com.lixingyong.music.server.netease.resource.model.entity.song.Song;
import java.util.List;
import lombok.Data;

/**
 * 单曲搜索列表
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class SongSearchList implements SearchList {
    /**
     * 本页音乐列表
     */
    private List<Song> songs;
    /**
     * 搜索到的音乐总数
     */
    private int songCount;
}
