package com.lixingyong.images.server.chevereto.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.lixingyong.images.service.CacheService;
import com.lixingyong.images.utils.ImagesApiException;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author LIlGG
 * @since 2022-10-22
 */
public class CheveretoCacheLoader implements CacheLoader<Object, Object> {

    private final static Logger logger = LoggerFactory.getLogger(CheveretoCacheLoader.class);

    private final ApplicationContext applicationContext;

    private final SpelExpressionParser spelExpressionParser;

    public CheveretoCacheLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.spelExpressionParser = new SpelExpressionParser();
    }

    @Override
    public @Nullable Object load(@NonNull Object key) throws Exception {
        logger.info("[Image]Chevereto -> 刷新 Key[{}] 缓存数据", key);
        ObjectProvider<CacheService<?>> provider = applicationContext.getBeanProvider(
            ResolvableType.forClass(CacheService.class));
        CacheService<?> cacheServiceBean = provider.stream()
            .filter(cacheService -> {
                try {
                    return key.equals(
                        spelExpressionParser.parseExpression(
                            Objects.requireNonNull(AnnotationUtils.findAnnotation(
                                cacheService.getClass().getMethod("getCacheData"),
                                Cacheable.class
                            )).key()).getValue());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return false;
            })
            .findFirst()
            .orElseThrow(() -> new ImagesApiException("未找到缓存原始方法，刷新缓存失败"));
        return cacheServiceBean.loadAndCacheData();
    }
}
