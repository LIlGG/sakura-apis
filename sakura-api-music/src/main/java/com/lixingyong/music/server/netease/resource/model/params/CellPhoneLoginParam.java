package com.lixingyong.music.server.netease.resource.model.params;

import lombok.Builder;
import lombok.Data;

/**
 * 使用手机号登录参数
 *
 * @author LIlGG
 * @since 2021/8/4
 */
@Builder
@Data
public class CellPhoneLoginParam {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码，这里需要是明文，在调用的时候会自动转为 md5 加密后的
     */
    private String password;
}
