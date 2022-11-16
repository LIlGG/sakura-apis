package com.lixingyong.images.server.chevereto.model.dto;

import com.lixingyong.images.server.chevereto.model.dto.base.BaseAlbumDTO;
import com.lixingyong.images.server.chevereto.model.dto.base.BaseUserDTO;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LIlGG
 * @since 2020/11/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseUserDTO {
    /**
     * 用户下属的相册
     */
    public List<BaseAlbumDTO> albums;
    /**
     * 用户创建时间
     */
    private Date date;
    /**
     * 用户网站地址
     */
    private String webSite;
    /**
     * 用户个人说明
     */
    private String bio;
    /**
     * 用户被赞量
     */
    private Long likes;
    /**
     * 用户点赞量
     */
    private Long liked;
    /**
     * 用户被关注量
     */
    private Long following;
    /**
     * 用户关注量
     */
    private Long followers;
    /**
     * 用户内容被观看数
     */
    private Long contentViews;
}
