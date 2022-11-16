package com.lixingyong.common.listener;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.event.ApplicationEventMulticaster;

/**
 * @author LIlGG
 * @since 2022-10-27
 */
public interface ApiApplicationEventMulticaster {
    ApplicationEventMulticaster getApplicationEventMulticaster(
        ConfigurableListableBeanFactory beanFactory);
}
