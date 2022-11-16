package com.lixingyong.images.service;

import java.util.List;

/**
 * 缓存服务
 *
 * @author LIlGG
 * @since 2022-10-18
 */
public interface CacheService<T> {
    /**
     * 获取当前服务下的全量缓存数据
     *
     * @return 全量缓存数据
     */
    List<T> getCacheData();

    /**
     * 加载并缓存数据
     *
     * @return 全量缓存数据
     */
    List<T> loadAndCacheData();
}
