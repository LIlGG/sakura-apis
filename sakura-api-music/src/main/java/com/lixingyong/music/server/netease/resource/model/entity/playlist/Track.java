package com.lixingyong.music.server.netease.resource.model.entity.playlist;

import lombok.Data;

/**
 * 歌单（playlist） 追踪信息，用来追踪歌单内所属音乐的编号
 *
 * @author LIlGG
 * @since 2021/7/29
 */
@Data
public class Track {
    /**
     * 歌曲编号
     */
    private long id;
}
