package com.lixingyong.images.server.chevereto.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 图片大小类型
 *
 * @author LIlGG
 * @date 2020/12/11
 */
public enum ImageSizeType implements ValueEnum<String> {
    /**
     * 原图
     */
    ORIGINAL("0"),
    /**
     * 中等图
     */
    MEDIUM("1"),
    /**
     * 缩略图
     */
    THUMBNAIL("2");

    private final String value;

    ImageSizeType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
