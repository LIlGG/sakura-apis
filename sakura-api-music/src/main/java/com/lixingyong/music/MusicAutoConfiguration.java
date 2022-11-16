package com.lixingyong.music;

import com.lixingyong.common.autoconfigure.module.ModuleApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

/**
 * @author LIlGG
 * @since 2022-09-21
 */

@ModuleApplication(MusicAutoConfiguration.MODULE_NAME)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "api.music", value = "enable", havingValue = "true", matchIfMissing = true)
public class MusicAutoConfiguration {
    public static final String MODULE_NAME = "Music";
}
