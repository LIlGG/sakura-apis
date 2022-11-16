package com.lixingyong.common.autoconfigure.channel;

import com.lixingyong.common.autoconfigure.ApiConfigurationRegistrarListener;
import com.lixingyong.common.autoconfigure.service.ApiService;
import com.lixingyong.common.autoconfigure.utils.ResourceUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author LIlGG
 * @since 2022-11-06
 */
public class ChannelServiceRegister implements ImportBeanDefinitionRegistrar, BeanFactoryAware,
    BeanClassLoaderAware {

    private static final ApiServiceBeanEntry EMPTY_ENTRY = new ApiServiceBeanEntry();

    private static final Log logger = LogFactory.getLog(ChannelServiceRegister.class);

    private BeanFactory beanFactory;

    private ClassLoader beanClassLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        if (!isEnabled(metadata)) {
            return;
        }
        getApiServiceBeanEntry(metadata).forEach(bean -> {
            registry.registerBeanDefinition(
                Objects.requireNonNull(bean.getBeanClassName()), bean);
        });
    }

    protected ApiServiceBeanEntry getApiServiceBeanEntry(AnnotationMetadata metadata) {
        if (!isEnabled(metadata)) {
            return EMPTY_ENTRY;
        }
        AnnotationAttributes attributes = getAttributes(metadata);
        List<Class<?>> services = getCandidateClasses(
            getCandidateServices(metadata, attributes), getBeanClassLoader()
        );
        return new ApiServiceBeanEntry(services);
    }

    protected List<Class<?>> getCandidateClasses(List<String> candidateServices,
                                               ClassLoader beanClassLoader) {
        return candidateServices.stream().map(service -> {
            try {
                return beanClassLoader.loadClass(service);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected List<String> getCandidateServices(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> services = new ArrayList<>(
            ResourceUtils.loadClassNameByAnnotation(ApiService.class,
                ClassUtils.getPackageName(metadata.getClassName())));

        ObjectProvider<ApiConfigurationRegistrarListener> registrar = getBeanFactory().getBeanProvider(
            ApiConfigurationRegistrarListener.class);
        registrar.stream().forEach(apiConfigurationRegistrar -> {
            apiConfigurationRegistrar.registerConfiguration(metadata, services);
        });

        if (logger.isDebugEnabled() && services.isEmpty()) {
            logger.debug("渠道 【" + metadata.getClassName() + "】 找不到服务类。如需添加需在服务类上添加 @ApiService 注解");
        }
        return services;
    }

    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        String name = getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
        Assert.notNull(attributes, () -> "找不到 ChannelAutoConfiguration 属性。 查看类 " + metadata.getClassName()
            + " 中的注解是否存在 " + ClassUtils.getShortName(name) + "?");
        return attributes;
    }

    protected Class<?> getAnnotationClass() {
        return ChannelApplication.class;
    }

    protected boolean isEnabled(AnnotationMetadata metadata) {
        return true;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    private static class ApiServiceBeanEntry implements Iterable<AnnotatedGenericBeanDefinition> {

        private final List<AnnotatedGenericBeanDefinition> annotatedGenericBeanDefinitions
            = new ArrayList<>();

        public ApiServiceBeanEntry() {
        }

        public ApiServiceBeanEntry(Class<?> serviceClasses) {
            createApiServiceBean(serviceClasses);
        }

        public ApiServiceBeanEntry(Collection<Class<?>> serviceClasses) {
            serviceClasses.forEach(this::createApiServiceBean);
        }

        public void createApiServiceBean(Class<?> serviceClass) {
           annotatedGenericBeanDefinitions.add(new AnnotatedGenericBeanDefinition(serviceClass));
        }

        @Override
        public Iterator<AnnotatedGenericBeanDefinition> iterator() {
            return annotatedGenericBeanDefinitions.iterator();
        }
    }
}
