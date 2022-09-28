package com.lixingyong.netease.resource.model.entity.search;

import com.lixingyong.netease.resource.model.entity.album.Album;
import java.util.List;
import lombok.Data;

/**
 * 专辑搜索列表
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class AlbumSearchList implements SearchList {
    /**
     * 本页专辑列表
     */
    private List<Album> albums;
    /**
     * 搜索到的专辑总数
     */
    private int albumCount;
}
