package com.lixingyong.images.advice;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.lixingyong.common.utils.IPUtil;

/**
 * 基于 {@link CaffeineCacheManager} 与 AOP 实现请求缓存功能。
 *
 * <p>
 * 针对于每个请求，实现幂等性，防止同一用户，同一时间大量请求某一接口触发雪崩等问题。
 * </p>
 *
 * @author LIlGG
 * @since 2022-10-21
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CacheAopAdvice {

    private final static String IMAGE_REQUEST_CACHE_NAME = "image-request-cache";

    private final Cache cache;

    private final HttpServletRequest request;

    public CacheAopAdvice(CaffeineCacheManager cacheManager, HttpServletRequest request) {
        this.request = request;
        cacheManager.registerCustomCache(IMAGE_REQUEST_CACHE_NAME, Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .weakValues()
            .build()
        );
        this.cache = Objects.requireNonNull(cacheManager.getCache(IMAGE_REQUEST_CACHE_NAME));
    }

    @Pointcut("execution(public * com.lixingyong.images.controller.*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 执行切点前，验证缓存中是否具有对应的缓存数据
        String ip = IPUtil.getIpAddr(request);
        String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        String url = request.getRequestURL().toString() + query;
        String key = ip + "-" + request.getHeader("Referer") + "-" + url;
        if (Objects.isNull(cache.get(key))) {
            // 执行方法，并保存缓存
            Object object = proceedingJoinPoint.proceed();
            if (Objects.nonNull(object)) {
                this.cache.put(key, object);
            }
        }
        return Objects.requireNonNull(cache.get(key)).get();
    }
}
