package com.lixingyong.images.advice;

import com.lixingyong.images.ImagesProperties;
import com.lixingyong.images.handler.ThumbHandler;
import com.lixingyong.images.handler.ThumbScheme;
import com.lixingyong.images.model.param.ThumbParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 图片链接处理 AOP，用于统一处理图片缩放、格式转换等功能。
 *
 * <p>
 * 链接处理需使用原始链接，因而有多个 AOP 环绕时，务必保证使用
 * {@link org.springframework.core.Ordered#LOWEST_PRECEDENCE} 使其始终处于 AOP 先返回数据阶段。
 * </p>
 *
 * @author LIlGG
 * @since 2022-10-21
 */
@Aspect
@Component
public class ThumbAopAdvice {

    private final ImagesProperties properties;

    public ThumbAopAdvice(ImagesProperties properties) {
        this.properties = properties;
    }

    @Pointcut("execution(public * com.lixingyong.images.controller.*.getUrlPath(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String path = (String) proceedingJoinPoint.proceed();
        ThumbScheme thumbScheme = properties.getThumbScheme();
        if (thumbScheme == ThumbScheme.NONE) {
            return path;
        }
        Class<? extends ThumbHandler> handler = thumbScheme.getValue();
        ThumbHandler thumbHandler = handler.getDeclaredConstructor().newInstance();
        ThumbParam param = (ThumbParam) proceedingJoinPoint.getArgs()[0];
        Integer width = param.getWidth();
        Integer height = param.getHeight();
        return thumbHandler.createBuild(path).resize(width, height).build();
    }
}
