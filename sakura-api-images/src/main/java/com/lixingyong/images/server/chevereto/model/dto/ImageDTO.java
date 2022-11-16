package com.lixingyong.images.server.chevereto.model.dto;

import com.lixingyong.images.server.chevereto.model.dto.base.BaseAlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseCategoryDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseImageDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseUserDTO;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LIlGG
 * @since 2020/11/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageDTO extends BaseImageDTO {
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片后缀类型
     */
    private String extension;
    /**
     * 图片大小（字节）
     */
    private int size;
    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片高度
     */
    private int height;
    /**
     * 图片上传日期
     */
    private Date date;
    /**
     * 图片详细描述
     */
    private String description;
    /**
     * 图片所属用户
     */
    private BaseUserDTO user;
    /**
     * 图片所属相册
     */
    private BaseAlbumDTO album;
    /**
     * 图片观看量
     */
    private Long views;
    /**
     * 图片所属分类
     */
    private BaseCategoryDTO category;
    /**
     * 图片被点赞量
     */
    private Long likes;
}
