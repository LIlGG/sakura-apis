package com.lixingyong.common.autoconfigure;

import java.util.List;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author LIlGG
 * @since 2022-11-06
 */
public interface ApiConfigurationRegistrarListener {

    /**
     * 注册 Configuration 时触发的标准通知。此处不能用于注册 configuration，而应当用于保存 configuration
     * 信息。
     *
     * @param importingClassMetadata 触发 configuration 加载的类
     * @param configuration 加载成功的 configuration
     */
    void registerConfiguration(AnnotationMetadata importingClassMetadata, List<String> configuration);
}
