package com.lixingyong.music.server.netease.resource.model.entity.lyric;

import java.io.Serializable;
import lombok.Data;

/**
 * 歌词实体
 *
 * @author LIlGG
 * @since 2021/8/4
 */
@Data
public class Lyric implements Serializable {
    /**
     * 当前歌词版本
     */
    private int version;
    /**
     * 歌词
     */
    private String lyric;
}
