package com.lixingyong.music.server.netease.resource.model.entity.playlist;

import lombok.Data;

/**
 * 歌单创建者详细信息
 *
 * @author LIlGG
 * @since 2021/7/29
 */
@Data
public class PlayListCreator {
    /**
     * 用户编号
     */
    private long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 签名
     */
    private String signature;
    /**
     * 描述
     */
    private String description;
    /**
     * 头像链接
     */
    private String avatarUrl;
}
