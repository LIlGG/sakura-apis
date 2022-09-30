package com.lixingyong.music.server.netease.resource.model.entity.search;

import com.lixingyong.music.server.netease.resource.model.entity.Artist;
import java.util.List;
import lombok.Data;

/**
 * 艺术家搜索列表，搜索到的有时候并不仅仅只是艺术家，也有可能是其他的，例如单曲、专辑等
 * 但格式是不变的
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class ArtistSearchList implements SearchList {
    /**
     * 本页艺术家列表
     */
    private List<Artist> artists;
    /**
     * 搜索到的艺术家总数
     */
    private int artistCount;
}
