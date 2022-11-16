package com.lixingyong.common.autoconfigure.channel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动 Channel（渠道）自动配置。自动化配置基于注解和包定义，通常将此注解置于 Channel Configuration 上。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableChannelAutoConfiguration
public @interface ChannelApplication {
    String value();

    /**
     * 是否为默认渠道。设置有唯一的默认渠道时，请求参数中可无需添加 server
     */
    boolean primary() default false;
}
