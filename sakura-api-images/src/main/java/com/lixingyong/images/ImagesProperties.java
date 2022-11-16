package com.lixingyong.images;

import com.lixingyong.images.handler.ThumbScheme;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIlGG
 * @since 2022-11-13
 */
@ConfigurationProperties(prefix = "api.images")
public class ImagesProperties {
    /**
     * 是否开启预缓存
     */
    private boolean preCache;

    /**
     * 图片缩略图提供方案
     */
    private ThumbScheme thumbScheme;

    public boolean isPreCache() {
        return preCache;
    }

    public void setPreCache(boolean preCache) {
        this.preCache = preCache;
    }

    public ThumbScheme getThumbScheme() {
        if (Objects.isNull(this.thumbScheme)) {
            this.thumbScheme = ThumbScheme.NONE;
        }
        return thumbScheme;
    }

    public void setThumbScheme(ThumbScheme thumbScheme) {
        this.thumbScheme = thumbScheme;
    }
}
