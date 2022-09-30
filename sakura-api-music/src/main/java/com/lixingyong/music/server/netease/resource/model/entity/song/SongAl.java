package com.lixingyong.music.server.netease.resource.model.entity.song;

import java.io.Serializable;
import lombok.Data;

/**
 * 单曲封面详情
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class SongAl implements Serializable {
    /**
     * 封面编号
     */
    private long id;
    /**
     * 封面名
     */
    private String name;
    /**
     * 封面链接
     */
    private String picUrl;
    /**
     * 封面编号
     */
    private long pic;
}
