package com.lixingyong.common.autoconfigure.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Indexed;

/**
 * 将 Service 扫描为 Bean 组件。 功能类似于 Spring 注解 {@link @Service}。但基于 API 自动配置的
 * 原理，使用自定义注解 {@link ApiService} 来加载 API 相关 Service。
 *
 * <p>
 * 使用当前注解后， Bean 的名称默认为全类名。
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ApiService {
}
