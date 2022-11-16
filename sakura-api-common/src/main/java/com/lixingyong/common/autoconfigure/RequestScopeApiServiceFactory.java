package com.lixingyong.common.autoconfigure;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * 拥有 Request scope 的 AutoConfigurationFactory。用于获取请求状态下的 {@code Service},
 * 此实例中将会与 HttpRequest 进行绑定。
 *
 * <p>
 * 此接口需要用户开启自动配置才能生效
 * </p>
 *
 * @see ApiAutoConfigurationFactory
 * @author LIlGG
 * @since 2022-11-08
 */
public interface RequestScopeApiServiceFactory extends ApiAutoConfigurationFactory {

    String SERVICE_PARAM_NAME = "server";

    /**
     * 获取服务 Bean
     *
     * @param requiredType 需要获取的目标服务详细类型
     * @param <T> 目标类型
     * @return 目标 Bean
     * @throws NoSuchBeanDefinitionException 如果没有对应的 Bean 定义
     * @throws BeanNotOfRequiredTypeException 如果 Bean 不是所需要的类型
     * @throws BeansException 如果无法创建 Bean
     */
    default <T> T getServiceBean(Class<T> requiredType) throws BeansException {
        throw new BeanInitializationException("实例还未初始化");
    }

    /**
     * 获取当前请求范围下的 Request
     *
     * @return 当前请求范围的 Request 实体
     */
    default HttpServletRequest getProxyHttpServletRequest() {
        throw new BeanInitializationException("实例还未初始化");
    }
}
