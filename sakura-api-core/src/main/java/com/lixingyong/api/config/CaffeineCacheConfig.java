package com.lixingyong.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIlGG
 * @since 2022-10-22
 */
@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    @Bean
    @ConditionalOnClass(CaffeineCacheManager.class)
    @ConditionalOnMissingBean
    public CaffeineCacheManager cacheManager() {
        return new CaffeineCacheManager();
    }
}
