package com.lixingyong.common.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author LIlGG
 * @since 2022-10-27
 */
public class SimpleApiApplicationEventMulticaster implements ApiApplicationEventMulticaster {

    private final static Logger logger = LoggerFactory.getLogger(SimpleApiApplicationEventMulticaster.class);

    private final static  String API_APPLICATION_EVENT_MULTICASTER = "apiApplicationEventMulticaster";

    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationEventMulticaster applicationEventMulticaster;

    private List<EventListenerFactory> eventListenerFactories;

    private final Set<Class<?>>
        nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    @Override
    public ApplicationEventMulticaster getApplicationEventMulticaster(
        ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!beanFactory.containsBean(API_APPLICATION_EVENT_MULTICASTER)) {
            initApplicationEventMulticaster();
        }
        applicationEventMulticaster = beanFactory.getBean(API_APPLICATION_EVENT_MULTICASTER, ApplicationEventMulticaster.class);
        setEventListenerFactories();
        applicationEventMulticaster.removeAllListeners();
        // 注册所有实现 ApplicationListener 的监听器
        registerApplicationListenerInterface();
        // 注册所有使用 @EventListener 的监听器
        registerEventListenerAnnotations();
        return applicationEventMulticaster;
    }

    private void setEventListenerFactories() {
        Map<String, EventListenerFactory> beans = beanFactory.getBeansOfType(EventListenerFactory.class, false, false);
        List<EventListenerFactory> factories = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(factories);
        this.eventListenerFactories = factories;
    }

    private void initApplicationEventMulticaster() {
        beanFactory.registerSingleton(API_APPLICATION_EVENT_MULTICASTER,
            new SimpleApplicationEventMulticaster(beanFactory));
    }

    private void registerApplicationListenerInterface() {
        beanFactory.getBeanProvider(ApplicationListener.class).forEach(
            applicationEventMulticaster::addApplicationListener);
    }

    private void registerEventListenerAnnotations() {
        String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> type = null;
                try {
                    type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
                } catch (Throwable ex) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                    }
                }
                if (type != null) {
                    if (ScopedObject.class.isAssignableFrom(type)) {
                        try {
                            Class<?> targetClass = AutoProxyUtils.determineTargetClass(
                                beanFactory, ScopedProxyUtils.getTargetBeanName(beanName));
                            if (targetClass != null) {
                                type = targetClass;
                            }
                        } catch (Throwable ex) {
                            // An invalid scoped proxy arrangement - let's ignore it.
                            if (logger.isDebugEnabled()) {
                                logger.debug("Could not resolve target bean for scoped proxy '" + beanName + "'", ex);
                            }
                        }
                    }
                    try {
                        processBean(beanName, type);
                    } catch (Throwable ex) {
                        throw new BeanInitializationException("Failed to process @EventListener " +
                            "annotation on bean with name '" + beanName + "'", ex);
                    }
                }
            }
        }
    }

    private void processBean(final String beanName, final Class<?> targetType) {
        if (!this.nonAnnotatedClasses.contains(targetType) &&
            AnnotationUtils.isCandidateClass(targetType, EventListener.class) &&
            !isSpringContainerClass(targetType)) {

            Map<Method, EventListener> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(targetType,
                    (MethodIntrospector.MetadataLookup<EventListener>) method ->
                        AnnotatedElementUtils.findMergedAnnotation(method, EventListener.class));
            } catch (Throwable ex) {
                // An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not resolve methods for bean with name '" + beanName + "'", ex);
                }
            }

            if (CollectionUtils.isEmpty(annotatedMethods)) {
                this.nonAnnotatedClasses.add(targetType);
                if (logger.isTraceEnabled()) {
                    logger.trace("No @EventListener annotations found on bean class: " + targetType.getName());
                }
            } else {
                // Non-empty set of methods
                List<EventListenerFactory> factories = this.eventListenerFactories;
                Assert.state(factories != null, "EventListenerFactory List not initialized");
                for (Method method : annotatedMethods.keySet()) {
                    for (EventListenerFactory factory : factories) {
                        if (factory.supportsMethod(method)) {
                            Method methodToUse = AopUtils.selectInvocableMethod(method, beanFactory.getType(beanName));
                            ApiApplicationListenerMethodAdapter applicationListener =
                                new ApiApplicationListenerMethodAdapter(beanName, targetType, methodToUse);
                            applicationListener.init(beanFactory);
                            applicationEventMulticaster.addApplicationListener(applicationListener);
                            break;
                        }
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(annotatedMethods.size() + " @EventListener methods processed on bean '" +
                        beanName + "': " + annotatedMethods);
                }
            }
        }
    }

    /**
     * Determine whether the given class is an {@code org.springframework}
     * bean class that is not annotated as a user or test {@link Component}...
     * which indicates that there is no {@link EventListener} to be found there.
     * @since 5.1
     */
    private static boolean isSpringContainerClass(Class<?> clazz) {
        return (clazz.getName().startsWith("org.springframework.") &&
            !AnnotatedElementUtils.isAnnotated(ClassUtils.getUserClass(clazz), Component.class));
    }
}
