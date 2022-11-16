package com.lixingyong.images.server.chevereto.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 相册可见度
 *
 * @author LIlGG
 * @since 2022-10-16
 */
public enum AlbumPrivacy implements ValueEnum<String> {
    /**
     * 公开的
     */
    PUBLIC("public"),
    /**
     * 需要使用密码
     */
    PASSWORD("password"),
    /**
     * 私有的
     */
    PRIVATE("private"),
    /**
     * 私有但可以使用链接访问
     */
    PRIVATE_BUT_LINK("private_but_link"),
    /**
     * 自定义
     */
    CUSTOM("custom");

    private final String value;

    AlbumPrivacy(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
