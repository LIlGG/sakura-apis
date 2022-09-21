package com.lixingyong.netease;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author LIlGG
 * @since 2022-09-21
 */
@Configuration
@EnableConfigurationProperties(NeteaseApiProperties.class)
public class NeteaseApiAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NeteaseApiAutoConfiguration.class);

    public static class AutoConfiguredBeanScannerRegistrar
        implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

        private BeanFactory beanFactory;

        private ResourceLoader resourceLoader;

        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {

            logger.debug("搜索当前目录下的 Bean 并注册至 Api-Core");

            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }

            List<String> packages = new ArrayList<>();
            packages.add(ClassUtils.getPackageName(this.getClass()));

            if (logger.isDebugEnabled()) {
                packages.forEach(pkg -> logger.debug("自动配置包： '{}'", pkg));
            }

            scanner.scan(StringUtils.toStringArray(packages));
        }

        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }


    @Import({AutoConfiguredBeanScannerRegistrar.class})
    public static class BeanScannerRegistrarNotFoundConfiguration {

        @PostConstruct
        public void afterPropertiesSet() {
            logger.debug("No Bean found.");
        }
    }
}
