package com.lixingyong.common.autoconfigure.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author LIlGG
 * @since 2022-11-05
 */
public class ResourceUtils {

    /**
     * 通配符。指当前目录及其子目录下的所有 class
     */
    private static final String ALL_RESOURCE_PATTERN = "/**/*.class";

    /**
     * 通配符。只获取当前目录下的 Class
     */
    private static final String DIRECTORY_CLASS_PATTERN = "/*.class";

    /**
     * 获取 {@code packName} 目录及其子目录下包含 {@code annotation} class 的类。返回其全类名
     *
     * @param annotation 类中需要包含的注解
     * @param packName 检索根目录
     * @return 目录下所有包含 {@code annotation} 的类
     */
    public static List<String> loadClassNameByAnnotation(Class<?> annotation, String packName) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(annotation.getClassLoader());
        String location = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
            + ClassUtils.convertClassNameToResourcePath(packName)
            + ALL_RESOURCE_PATTERN;
        MetadataReader[] readers = findAnnotationReader(resourcePatternResolver, annotation, location);
        return Arrays.stream(readers).map(item -> item.getClassMetadata().getClassName()).collect(
            Collectors.toList());
    }

    /**
     * 逆序获取包含指定注解的注解元数据。由于逆序遍历的特殊性，因此只允许查找唯一的一个，且以包的深度优先。例如
     *
     * <p>
     * 包 <code>com.lixingyong.music</code> 与包 <code>com.lixingyong.music.controller</code>
     * 下均有使用某个注解的类，那么最终查询结果将只返回 <code>com.lixingyong.music.controller</code> 下的注解元数据
     * </p>
     *
     * <p>
     * 当包名小于两位时终止检索，并返回 {@code null}。例如 <code>com.lixingyong</code> 时将返回 null
     * </p>
     * @param annotation 需要检索的注解
     * @param packName 包名
     * @return 查询出的注解元数据
     */
    @Nullable
    public static AnnotationMetadata reverseLoadClassNameByAnnotation(Class<?> annotation, String packName) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(annotation.getClassLoader());
        while(true) {
            if (StringUtils.countOccurrencesOf(packName, ".") < 2) {
                return null;
            }
            String location = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(packName) +
                DIRECTORY_CLASS_PATTERN;
            MetadataReader[] readers = findAnnotationReader(resourcePatternResolver, annotation, location);
            if (readers.length > 1) {
                throw new RuntimeException("逆向查找到多个包含 [" + annotation.getName() + "] 注解类");
            }
            if (readers.length == 1) {
                return readers[0].getAnnotationMetadata();
            }
            packName = packName.substring(0, packName.lastIndexOf("."));
        }
    }

    public static MetadataReader[] findAnnotationReader(ResourcePatternResolver resourcePatternResolver,
                                                        Class<?> annotation,
                                                        String location) {
        List<MetadataReader> readers = new ArrayList<>();
        try {
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            Resource[] resources = resourcePatternResolver.getResources(location);
            for(Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                if (reader.getAnnotationMetadata().hasAnnotation(annotation.getName())) {
                    readers.add(reader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readers.toArray(MetadataReader[]::new);
    }
}
