package com.lixingyong.images.server.chevereto.cache;

/**
 * @author LIlGG
 * @since 2022-10-16
 */
public class CheveretoCacheConstant {
    /**
     * 最大缓冲缓存时间（秒）
     */
    public static final Long MAX_CACHE_TIME = 30 * 60L;
    /**
     * 缓存刷新时间（秒）
     */
    public static final Long REFRESH_CACHE_TIME = 10 * 60L;
    /**
     * chevereto 缓存名
     */
    public static final String CHEVERETO_CACHE_NAME = "image-chevereto-cache";
    /**
     * 公共图片列表缓存key
     */
    public static final String PUBLIC_IMAGE_ALL_CACHE_KEY = "public-image-all";
    /**
     * 公共相册列表缓存key
     */
    public static final String PUBLIC_ALBUM_ALL_CACHE_KEY = "public-album-all";
    /**
     * 分类列表缓存key
     */
    public static final String PUBLIC_CATEGORY_ALL_CACHE_KEY = "public-category-all";
    /**
     * 用户列表缓存key
     */
    public static final String PUBLIC_USER_ALL_CACHE_KEY = "public-user-all";
}
