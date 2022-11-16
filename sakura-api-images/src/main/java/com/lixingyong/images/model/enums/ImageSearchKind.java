package com.lixingyong.images.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 图片检索方式
 *
 * @author LIlGG
 * @since 2020/11/24
 */
public enum ImageSearchKind implements ValueEnum<String> {
    /**
     * 随机
     */
    RANDOM("random"),
    /**
     * 按顺序
     */
    ORDER("order");

    private final String value;

    ImageSearchKind(String value){
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
