package com.lixingyong.images.listener;

import com.lixingyong.images.ImagesProperties;
import com.lixingyong.images.service.CacheService;
import com.lixingyong.images.service.ImagesApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author LIlGG
 * @since 2022-10-31
 */
@Component
public class CacheListener {
    private final static Logger logger = LoggerFactory.getLogger(CacheListener.class);

    public final ApplicationContext applicationContext;

    private final ImagesProperties properties;

    public CacheListener(ApplicationContext applicationContext, ImagesProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onServerLoadService(ContextRefreshedEvent event) {
        if (!this.properties.isPreCache()) {
            return;
        }
        logger.info("开始预缓存数据");
        applicationContext.getBeanProvider(ImagesApiService.class, true)
            .stream()
            .filter(bean -> CacheService.class.isAssignableFrom(bean.getClass()))
            .forEach(CacheService::getCacheData);
    }
}
