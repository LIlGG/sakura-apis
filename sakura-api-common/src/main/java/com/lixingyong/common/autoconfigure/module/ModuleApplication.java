package com.lixingyong.common.autoconfigure.module;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启用 API Module（API 模块） 自动化配置。自动化配置通常基于注解所在包及自定义的 Bean。因此
 * 建议将此注解放在各 API 模块的根包及 AutoConfiguration 文件上。
 *
 * @see AutoConfiguration
 * @see EnableModuleAutoConfiguration
 * @see ComponentScan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfiguration
@EnableModuleAutoConfiguration
@ComponentScan
public @interface ModuleApplication {
    String value();
}
