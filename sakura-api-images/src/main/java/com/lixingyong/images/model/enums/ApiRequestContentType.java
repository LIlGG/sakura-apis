package com.lixingyong.images.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 随机图请求时所支持内容类型
 *
 * <p>API 需要严格控制入参，因此设置此枚举类保证入参正确。
 * 但因此带来的问题是每次在 {@link com.lixingyong.images.controller.ImagesController} 中
 * 添加额外的请求地址时，都需要在当前枚举类中注册</p>
 *
 * @author LIlGG
 * @since 2022/10/2
 */
public enum ApiRequestContentType implements ValueEnum<String> {
    /**
     * 获取图片地址列表
     */
    URLS("urls"),
    /**
     * 获取非图片列表
     */
    LIST("list"),
    /**
     * 获取详细信息
     */
    DETAIL("detail"),
    /**
     * 获取图片地址
     */
    URL("url");

    /**
     * 请求类型
     */
    final String type;

    ApiRequestContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getValue() {
        return type;
    }
}
