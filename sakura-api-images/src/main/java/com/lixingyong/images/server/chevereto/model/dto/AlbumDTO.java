package com.lixingyong.images.server.chevereto.model.dto;

import com.lixingyong.images.server.chevereto.model.dto.base.BaseAlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseUserDTO;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LIlGG
 * @since 2020/11/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AlbumDTO extends BaseAlbumDTO {
    /**
     * 相册所属用户基础信息
     */
    private BaseUserDTO user;
    /**
     * 相册创建日期
     */
    private Date date;
    /**
     * 相册详细说明
     */
    private String description;
    /**
     * 相册点赞数
     */
    private Long likes;
    /**
     * 相册查看量
     */
    private Long views;
}
