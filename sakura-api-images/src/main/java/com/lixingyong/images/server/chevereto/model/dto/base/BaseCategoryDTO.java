package com.lixingyong.images.server.chevereto.model.dto.base;
import lombok.Data;

/**
 * @author LIlGG
 * @since 2020/11/27
 */
@Data
public class BaseCategoryDTO implements BaseDTO{
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类 url key
     */
    private String urlKey;
    /**
     * 分类 URL
     */
    private String url;
}
