package com.lixingyong.images.server.chevereto.model.dto.base;

import lombok.Data;

/**
 * @author LIlGG
 * @since 2020/11/27
 */
@Data
public class BaseAlbumDTO implements BaseDTO {
    /**
     * 相册 编号
     */
    private String code;
    /**
     * 相册名称
     */
    private String name;
    /**
     * 相册链接
     */
    private String url;
    /**
     * 相册所包含的图片数量
     */
    private Long imageCount;
}
