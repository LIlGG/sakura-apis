package com.lixingyong.images.handler;

import com.lixingyong.common.utils.ValueEnum;

/**
 *
 * @author LIlGG
 * @since 2022-11-13
 */
public enum ThumbScheme implements ValueEnum<Class<? extends ThumbHandler>> {
    /**
     * 无缩放功能
     */
    NONE(null),

    /**
     * 使用 阿里云 图片缩放功能
     */
    ALI_CLOUD(AliCloudThumbHandler.class);

    private final Class<? extends ThumbHandler> thumbHandler;

    ThumbScheme(Class<? extends ThumbHandler> thumbHandler) {
        this.thumbHandler = thumbHandler;
    }

    @Override
    public Class<? extends ThumbHandler> getValue() {
        return this.thumbHandler;
    }
}
