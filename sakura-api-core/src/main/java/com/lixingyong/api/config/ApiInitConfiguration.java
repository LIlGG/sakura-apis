package com.lixingyong.api.config;

import com.lixingyong.common.autoconfigure.AbstractApiApplicationContext;
import com.lixingyong.common.autoconfigure.AbstractApiConfigurationRegistrarListener;
import com.lixingyong.common.autoconfigure.ApiApplicationContext;
import com.lixingyong.common.autoconfigure.RequestScopeApiServiceFactory;
import com.lixingyong.common.autoconfigure.module.ModuleApplication;
import com.lixingyong.common.autoconfigure.utils.ResourceUtils;
import java.util.Objects;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * API 初始化配置，用于监听自动配置注册事件。并创建 ApiApplicationContext
 *
 * @author LIlGG
 * @since 2022-11-08
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class ApiInitConfiguration extends AbstractApiConfigurationRegistrarListener {

    @Bean
    public ApiApplicationContext applicationContext() {
        GenericApiApplicationContext applicationContext = new GenericApiApplicationContext();
        applicationContext.setRootAutoConfiguration(super.rootAutoConfiguration);
        return applicationContext;
    }

    /**
     * @author LIlGG
     * @since 2022-11-07
     */
    private static class GenericApiApplicationContext extends AbstractApiApplicationContext implements ApplicationContextAware {
        private ApplicationContext applicationContext;

        private BeanFactory beanFactory;

        @Override
        public ApplicationContext getSpringApplicationContext() {
            return this.applicationContext;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public BeanFactory getBeanFactory() {
            return this.beanFactory;
        }

        @Override
        public <T> T getServiceBean(Class<T> requiredType) throws BeansException {
            if (Objects.isNull(getProxyHttpServletRequest())) {
                return super.getServiceBean(requiredType);
            }
            String moduleName = getServiceWhereModuleName(requiredType);
            if (Objects.isNull(moduleName)) {
                return super.getServiceBean(requiredType);
            }
            String channelName = getRequestWhereChannelName(getProxyHttpServletRequest());
            return super.getServiceBean(moduleName, channelName, requiredType);
        }

        @Override
        public HttpServletRequest getProxyHttpServletRequest() {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                return ((ServletRequestAttributes) requestAttributes).getRequest();
            }
            return null;
        }

        @Nullable
        protected String getRequestWhereChannelName(ServletRequest request) {
            return request.getParameter(RequestScopeApiServiceFactory.SERVICE_PARAM_NAME);
        }

        @Nullable
        protected String getServiceWhereModuleName(Class<?> clazz) {
            String packageName = ClassUtils.getPackageName(clazz);
            // 逐级依次往上获取包下的类
            AnnotationMetadata metadata = ResourceUtils.reverseLoadClassNameByAnnotation(ModuleApplication.class, packageName);
            if (Objects.isNull(metadata)) {
                return null;
            }
            AnnotationAttributes attributes = ApiInitConfiguration.getAnnotationAttributes(metadata, ModuleApplication.class.getName());
            return attributes.getString("value");
        }
    }
}
