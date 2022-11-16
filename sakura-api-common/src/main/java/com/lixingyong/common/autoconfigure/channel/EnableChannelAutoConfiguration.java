package com.lixingyong.common.autoconfigure.channel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 当您使用 {@link EnableChannelAutoConfiguration} 注解时，系统将尽可能的智能化配置 Channel 及其
 * 关联的 Service。
 *
 * <p>
 * 此注解会通过 {@link ChannelServiceRegister} 来自动加载当前 Channel 下的 Service，用来提供最终的
 * 接口服务。需注意的是， Service 上的注解需要为 {@link com.lixingyong.common.autoconfigure.service.ApiService}
 * 而不是标准的 Spring 注解。否则无法找到对应的 Service。自动加载也将失败。
 * </p>
 *
 * @see ChannelServiceRegister
 * @see com.lixingyong.common.autoconfigure.service.ApiService
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ChannelServiceRegister.class)
public @interface EnableChannelAutoConfiguration {
}
