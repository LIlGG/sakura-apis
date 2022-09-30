package com.lixingyong.music.server.netease.resource.model.entity.login;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户详细信息
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class Account implements Serializable {
    /**
     * 用户编号
     */
    private long id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户状态
     */
    private int status;
    /**
     * 用户创建时间
     */
    private long createTime;
    /**
     * 加密 salt
     */
    private String salt;
    /**
     * VIP 等级
     */
    private int vipType;
}
