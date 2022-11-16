package com.lixingyong.common.autoconfigure;

import org.springframework.context.ApplicationContext;

/**
 * 为 API 应用程序提供上下文中心接口。
 *
 * <p>ApiApplicationContext 提供如下支持</p>
 * <ul>
 *     <li>用于提供获取自动配置文件路径的功能，由 {@link ApiAutoConfigurationFactory} 提供</li>
 *     <li>提供 SpringBoot ApplicationContext</li>
 * </ul>
 *
 * @see ApiAutoConfigurationFactory
 * @author LIlGG
 * @since 2022-11-06
 */
public interface ApiApplicationContext extends RequestScopeApiServiceFactory {
    /**
     * 获取 SpringApplicationContext
     *
     * @see ApplicationContext
     * @return ApplicationContext
     */
    ApplicationContext getSpringApplicationContext();
}
