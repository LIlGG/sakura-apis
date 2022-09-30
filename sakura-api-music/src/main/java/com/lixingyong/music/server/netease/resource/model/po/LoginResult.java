package com.lixingyong.music.server.netease.resource.model.po;

import com.lixingyong.music.server.netease.resource.model.entity.login.Account;
import com.lixingyong.music.server.netease.resource.model.entity.login.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录返回接口
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginResult extends BaseNeteaseResult {
    /**
     * 用户信息
     */
    private Account account;
    /**
     * token
     */
    private String token;
    /**
     * 用户设置信息
     */
    private Profile profile;
    /**
     * Cookie
     */
    private String cookie;
}
