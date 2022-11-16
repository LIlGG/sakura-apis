package com.lixingyong.images;

import com.lixingyong.common.autoconfigure.module.ModuleApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
@ModuleApplication(ImagesAutoConfiguration.MODULE_NAME)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "api.images", value = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ImagesProperties.class)
public class ImagesAutoConfiguration {
    public static final String MODULE_NAME = "Images";
}
