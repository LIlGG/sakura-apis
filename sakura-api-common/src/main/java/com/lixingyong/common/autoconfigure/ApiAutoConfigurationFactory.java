package com.lixingyong.common.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * 用于访问 API 自动配置文件的接口。
 *
 * @author LIlGG
 * @since 2022-11-07
 */
public interface ApiAutoConfigurationFactory extends BeanFactoryAware {

    /**
     * 获取所有 Module 自动配置文件
     * @return 全类名组成的数组
     */
    AutoConfigurationEntry[] getModuleConfigurations();

    /**
     * 获取渠道自动配置文件。
     *
     * @param moduleName 模块名称
     * @return 全类名组成的数组
     */
    AutoConfigurationEntry[] getChannelConfigurations(String moduleName);

    /**
     * 获取服务自动配置文件
     *
     * @param moduleName 模块名称
     * @param channelName 渠道名称
     * @return 全类名组成的数组
     */
    AutoConfigurationEntry[] getServices(String moduleName, String channelName);

    /**
     * 获取服务 Bean
     *
     * @param moduleName  模块名称
     * @param channelName 渠道名称
     * @param requiredType 需要获取的目标服务详细类型
     * @param <T> 目标类型
     * @return 目标 Bean
     * @throws NoSuchBeanDefinitionException 如果没有对应的 Bean 定义
     * @throws BeanNotOfRequiredTypeException 如果 Bean 不是所需要的类型
     * @throws BeansException 如果无法创建 Bean
     */
    <T> T getServiceBean(String moduleName, String channelName, Class<T> requiredType) throws BeansException;

    /**
     * 获取 SpringBoot Bean 工厂
     *
     * @return Bean 工厂
     */
    BeanFactory getBeanFactory();
}
