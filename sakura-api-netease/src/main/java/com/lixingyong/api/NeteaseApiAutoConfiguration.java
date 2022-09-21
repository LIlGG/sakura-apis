package com.lixingyong.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIlGG
 * @since 2022-09-21
 */
@Configuration
@EnableConfigurationProperties(NeteaseApiProperties.class)
public class NeteaseApiAutoConfiguration {
}
