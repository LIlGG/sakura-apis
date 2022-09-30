package com.lixingyong.music.server.netease.resource.model.entity.album;

import com.lixingyong.music.server.netease.resource.model.entity.Artist;
import com.lixingyong.music.server.netease.resource.model.entity.song.Song;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 专辑详情
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class Album implements Serializable {
    /**
     * 专辑编号
     */
    private long id;
    /**
     * 专辑名称
     */
    private String name;
    /**
     * 专辑类型名
     */
    private String type;
    /**
     * 专辑内歌曲数量
     */
    private int size;
    /**
     * 专辑封面编号
     */
    private long pic;
    /**
     * 专辑封面链接
     */
    private String picUrl;
    /**
     * 专辑别名
     */
    private String[] alias;
    /**
     * 专辑艺术家信息
     */
    private Artist artist;
    /**
     * 专辑歌曲 - 搜索到的专辑不会将歌曲显示
     */
    private List<Song> songs;
}
