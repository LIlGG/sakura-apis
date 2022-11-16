package com.lixingyong.common.autoconfigure.module;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * 当您使用 {@code EnableModuleAutoConfiguration} 注解时，系统将尽可能采取智能化的方式来自动配置目标
 * 模块下的 Channel（渠道）
 * </p>
 *
 * <p>
 * 通常来说，此注解所在的包路径是具有特殊意义的。由于渠道配置一般都是懒加载，因此无法使用 Spring 自带注解，例如
 * {@code @Configuration} 来进行配置。并且由于模块包推荐与主命名空间保持分割，因此也无法通过主命名空间的
 * {@code @SpringBootApplication} 来扫描。因此，推荐将此注解所在配置放入到各模块的根包中，以便扫描所有子包和类。
 * </p>
 *
 * <p>
 * 此注解还会通过 {@link ModuleAutoConfigurationSelector} 来扫描当前模块下的所有 Channel。扫描的前提是目标
 * Channel 配置类上具有 {@link com.lixingyong.common.autoconfigure.channel.ChannelApplication} 注解。
 * 否则将视为 Module 下无任何 Channel。
 * </p>
 *
 * @see AutoConfiguration
 * @see AutoConfigurationPackage
 * @see ModuleAutoConfigurationSelector
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(ModuleAutoConfigurationSelector.class)
public @interface EnableModuleAutoConfiguration {
}
