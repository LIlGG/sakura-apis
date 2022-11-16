package com.lixingyong.images.server.chevereto.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * chevereto 用户状态
 *
 * @author LIlGG
 * @since 2022-10-17
 */
public enum UserStatus implements ValueEnum<String> {
    /**
     * 已验证
     */
    VALID("valid"),
    /**
     * 等待确认
     */
    AWAITING_CONFIRMATION("awaiting-confirmation"),
    /**
     * 等待邮箱
     */
    AWAITING_EMAIL("awaiting-email"),
    /**
     * 已被禁止
     */
    BANNED("banned");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
