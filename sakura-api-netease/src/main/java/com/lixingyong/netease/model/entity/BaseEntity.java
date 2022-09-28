package com.lixingyong.netease.model.entity;

import lombok.Data;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
@Data
public abstract class BaseEntity implements Entity {
    /**
     * 歌单/歌曲编号
     */
    private long id;

    /**
     * 歌单/歌曲名称
     */
    private String name;
}
