package com.lixingyong.netease.resource.model.entity.login;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户个人设置属性
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class Profile implements Serializable {
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 头像链接
     */
    private String avatarUrl;
    /**
     * 头像编号
     */
    private long avatarImgId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户签名
     */
    private String signature;
}
