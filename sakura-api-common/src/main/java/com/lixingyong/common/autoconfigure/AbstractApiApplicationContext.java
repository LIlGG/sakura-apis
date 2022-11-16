package com.lixingyong.common.autoconfigure;

import com.lixingyong.common.exception.ApiException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author LIlGG
 * @since 2022-11-07
 */
public abstract class AbstractApiApplicationContext implements ApiApplicationContext {

    private List<AutoConfigurationEntry> rootAutoConfiguration;

    @Override
    public AutoConfigurationEntry[] getModuleConfigurations() {
        return this.rootAutoConfiguration
            .toArray(AutoConfigurationEntry[]::new);
    }

    @Override
    public AutoConfigurationEntry[] getChannelConfigurations(String moduleName) {
        Assert.notNull(moduleName, "模块名不能为空");
        return getModuleConfigurations(this.rootAutoConfiguration, moduleName)
            .getChild()
            .toArray(AutoConfigurationEntry[]::new);
    }

    @Override
    public AutoConfigurationEntry[] getServices(String moduleName, String channelName) {
        Assert.notNull(moduleName, "模块名不能为空");
        Assert.notNull(channelName, "渠道名不能为空");
        return getChannelConfigurations(this.rootAutoConfiguration, moduleName, channelName)
            .getChild()
            .toArray(AutoConfigurationEntry[]::new);
    }

    @Override
    public <T> T getServiceBean(String moduleName, @Nullable String channelName, Class<T> instanceType)
        throws BeansException {
        Assert.notNull(moduleName, "模块名不能为空");
        AutoConfigurationEntry defaultChannel;
        if (!StringUtils.hasLength(channelName)) {
            AutoConfigurationEntry module = getModuleConfigurations(this.rootAutoConfiguration, moduleName);
            try {
                channelName = getDefaultChildConfigurations(module).getName();
                if (!StringUtils.hasLength(channelName)) {
                    throw new ApiException("当前渠道未启用");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        List<String> serviceClasses = Arrays.stream(getServices(moduleName, channelName))
            .map(AutoConfigurationEntry::getClassName)
            .collect(Collectors.toList());
        if (serviceClasses.isEmpty()) {
            throw new ApiException("该渠道下不存在目标 Bean");
        }
        return getSpringApplicationContext().getBeanProvider(instanceType)
            .stream()
            .filter(bean -> serviceClasses.contains(AopProxyUtils.ultimateTargetClass(bean).getName()))
            .findFirst()
            .orElseThrow(() -> new ApiException("未能够获取到 Bean"));
    }

    /**
     * 获取默认子配置。具有以下场景时，将能够获取到默认子配置。
     * <ul>
     *     <li>该配置类下仅存在一个子配置。此时默认子配置为唯一</li>
     *     <li>该配置类下有多个子配置，但有且仅有一个子配置设置为默认选项</li>
     * </ul>
     *
     * <p>
     * 若该配置下无子配置，则抛出 {@link ApiException} 异常
     * </p>
     *
     * @param parentConfiguration 需查找默认子配置的配置项
     * @return 查询出的唯一子配置
     * @throws ApiException 无子配置或有多个子配置
     */
    protected AutoConfigurationEntry getDefaultChildConfigurations(AutoConfigurationEntry parentConfiguration) throws
        ApiException {
        if (parentConfiguration.getChild().size() > 1) {
            return getPrimaryChildConfiguration(parentConfiguration);
        }
        if (parentConfiguration.getChild().size() == 1) {
            return parentConfiguration.getChild().get(0);
        }
        throw new ApiException("查找默认子配置失败。当前配置 [" + parentConfiguration + "] 下不存在子配置");
    }

    protected AutoConfigurationEntry getPrimaryChildConfiguration(
        AutoConfigurationEntry parentConfiguration) {
        List<AutoConfigurationEntry> child = parentConfiguration.getChild()
            .stream()
            .filter(AutoConfigurationEntry::isDefault)
            .collect(Collectors.toList());
        if (child.isEmpty()) {
            throw new ApiException("当前配置 [" + parentConfiguration + "] 无默认配置，请手动指定");
        }
        if (child.size() > 1) {
            throw new ApiException("当前配置 [" + parentConfiguration + "] 具有多个默认配置，请手动指定");
        }
        return child.get(0);
    }

    protected AutoConfigurationEntry getModuleConfigurations(List<AutoConfigurationEntry> configurations, String moduleName) {
        return configurations
            .stream()
            .filter(item -> moduleName.equalsIgnoreCase(item.getName()))
            .findAny()
            .orElseThrow(() -> new NoSuchBeanDefinitionException("不存在模块 [" + moduleName + "]"));
    }

    protected AutoConfigurationEntry getChannelConfigurations(List<AutoConfigurationEntry> configurations, String moduleName, String channelName) {
        return getModuleConfigurations(configurations, moduleName)
            .getChild()
            .stream()
            .filter(item -> channelName.equalsIgnoreCase(item.getName()))
            .findAny()
            .orElseThrow(() -> new NoSuchBeanDefinitionException("不存在渠道 [" + channelName + "]"));
    }

    public void setRootAutoConfiguration(List<AutoConfigurationEntry> rootAutoConfiguration) {
        this.rootAutoConfiguration = rootAutoConfiguration;
    }
}
