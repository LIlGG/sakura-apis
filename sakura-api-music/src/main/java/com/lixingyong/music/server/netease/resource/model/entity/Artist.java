package com.lixingyong.music.server.netease.resource.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 艺术家信息
 *
 * 这里包括大部分信息，但有的接口不会给全
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class Artist implements Serializable {
    /**
     * 艺术家编号
     */
    private long id;
    /**
     * 艺术家名称
     */
    private String name;
    /**
     * 艺术家别称
     */
    private String[] alias;
    /**
     * 艺术家封面编号
     */
    private long picId;
    /**
     * 艺术家封面
     */
    private String picUrl;
    /**
     * 艺术家发表歌曲数量
     */
    private int musicSize;
}
