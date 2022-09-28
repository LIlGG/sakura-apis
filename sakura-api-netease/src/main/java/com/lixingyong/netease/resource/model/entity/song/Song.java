package com.lixingyong.netease.resource.model.entity.song;

import com.lixingyong.netease.resource.model.entity.Artist;
import java.io.Serializable;
import java.util.Collection;
import lombok.Data;

/**
 * 查询出的歌曲详情，
 * 非完整歌曲详情，其内不包括音乐链接
 *
 * 若想查询出完整音乐信息（包括链接），则需要使用 {@link com.lixingyong.netease.resource.NeteaseNodeJs#getMusicUrl(Collection)}
 * 来使用音乐编号获取 URL
 * @see com.lixingyong.netease.resource.NeteaseNodeJs#getMusicUrl(Collection)
 * @author LIlGG
 * @since 2021/7/29
 */
@Data
public class Song implements Serializable {
    /**
     * 歌曲编号
     */
    private long id;
    /**
     * 歌曲名
     */
    private String name;
    /**
     * 艺术家信息
     */
    private Artist[] ar;
    /**
     * 歌曲别名列表
     */
    private String[] alia;
    /**
     * 歌曲封面详情
     */
    private SongAl al;
}
