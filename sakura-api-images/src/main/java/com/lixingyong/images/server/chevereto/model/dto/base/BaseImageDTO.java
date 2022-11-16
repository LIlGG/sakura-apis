package com.lixingyong.images.server.chevereto.model.dto.base;
import lombok.Data;

/**
 * @author LIlGG
 * @since 2020/11/25
 */
@Data
public class BaseImageDTO implements BaseDTO {
    /**
     * 图片编号
     */
    private String code;
    /**
     * 图片链接
     */
    private String url;
    /**
     * 中等图片链接
     */
    private String mediumUrl;
    /**
     * 缩略图图片链接
     */
    private String thumbnailUrl;
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片所属相册名称
     */
    private String albumName;
    /**
     * 图片所属分类名称
     */
    private String categoryName;
}
