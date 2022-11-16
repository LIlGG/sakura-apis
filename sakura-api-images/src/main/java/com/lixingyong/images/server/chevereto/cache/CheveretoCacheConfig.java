package com.lixingyong.images.server.chevereto.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author LIlGG
 * @since 2022-10-22
 */
@EnableCaching
public class CheveretoCacheConfig {

    @Bean
    public CheveretoCacheLoader cacheLoader(ApplicationContext applicationContext,
                                            CaffeineCacheManager cacheManager) {
        CheveretoCacheLoader cheveretoCacheLoader = new CheveretoCacheLoader(applicationContext);
        cacheManager.registerCustomCache(CheveretoCacheConstant.CHEVERETO_CACHE_NAME,
            Caffeine.newBuilder()
                .expireAfterWrite(CheveretoCacheConstant.MAX_CACHE_TIME, TimeUnit.SECONDS)
                .refreshAfterWrite(CheveretoCacheConstant.REFRESH_CACHE_TIME, TimeUnit.SECONDS)
                .build(cheveretoCacheLoader)
        );
        return cheveretoCacheLoader;
    }
}
