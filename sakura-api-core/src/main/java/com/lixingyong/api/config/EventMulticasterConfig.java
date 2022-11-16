package com.lixingyong.api.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

/**
 * @author LIlGG
 * @since 2022-10-27
 */
@Configuration
public class EventMulticasterConfig {
    @Bean
    @ConditionalOnMissingBean(ApplicationEventMulticaster.class)
    public ApplicationEventMulticaster applicationEventMulticaster(BeanFactory beanFactory) {
        return new SimpleApplicationEventMulticaster(beanFactory);
    }
}
