package com.lixingyong.common.autoconfigure.module;

import com.lixingyong.common.autoconfigure.ApiConfigurationRegistrarListener;
import com.lixingyong.common.autoconfigure.channel.ChannelApplication;
import com.lixingyong.common.autoconfigure.utils.ResourceUtils;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * {@link DeferredImportSelector} 的自动处理配置。用于模块加载。
 *
 * @see EnableModuleAutoConfiguration
 * @see AutoConfigurationImportSelector
 * @author LIlGG
 * @since 2022-11-03
 */
public class ModuleAutoConfigurationSelector extends AutoConfigurationImportSelector {

    private static final Log logger = LogFactory.getLog(ModuleAutoConfigurationSelector.class);

    @Override
    protected boolean isEnabled(AnnotationMetadata metadata) {
        if (getClass() == ModuleAutoConfigurationSelector.class) {
            // 自定义开启校验的规则，例如从 metadata 中获取属性来校验。
            return true;
        }
        return true;
    }

    @Override
    protected Class<?> getAnnotationClass() {
        return EnableModuleAutoConfiguration.class;
    }

    @Override
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> configurations = new ArrayList<>(
            ResourceUtils.loadClassNameByAnnotation(ChannelApplication.class,
                ClassUtils.getPackageName(metadata.getClassName())));

        ObjectProvider<ApiConfigurationRegistrarListener> registrar = getBeanFactory().getBeanProvider(
            ApiConfigurationRegistrarListener.class);
        registrar.stream().forEach(apiConfigurationRegistrar -> {
            apiConfigurationRegistrar.registerConfiguration(metadata, configurations);
        });

        if (logger.isDebugEnabled() && configurations.isEmpty()) {
            logger.debug("模块 【" + metadata.getClassName() + "】 找不到渠道自动配置类。如需添加需在渠道自动配置类上添加 @ChannelApplication 注解");
        }
        return configurations;
    }

    @Override
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableModuleAutoConfiguration.class;
    }

    @Override
    protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        return new LinkedHashSet<>();
    }
}
