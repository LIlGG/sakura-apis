package com.lixingyong.images.server.chevereto.model.entity;

import com.lixingyong.images.server.chevereto.converter.UserStatusConverter;
import com.lixingyong.images.server.chevereto.model.enums.UserStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Chevereto 用户映射（不包括全部数据）
 * 仅包括已经完全验证的，并且用户不为匿名的（但匿名的，可以查看获取对应的相册数据）
 *
 * @author LIlGG
 * @since 2022-10-16
 */
@Table(name = "chv_users")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity extends BaseEntity{
    /**
     * 用户 ID
     */
    @Id
    @Column(name = "user_id")
    private Long id;
    /**
     * 用户昵称（可能为空）
     */
    @Column(name = "user_name")
    private String name;
    /**
     * 经过处理后的用户名（可以用来查询信息）
     */
    @Column(name = "user_username")
    private String username;
    /**
     * 用户 url
     */
    @Transient
    private String url;
    /**
     * 用户创建时间
     */
    @Column(name = "user_date")
    private Date date;
    /**
     * 用户网站地址
     */
    @Column(name = "user_website")
    private String webSite;
    /**
     * 用户个人说明
     */
    @Column(name = "user_bio")
    private String bio;
    /**
     * 用户状态
     */
    @Convert(converter = UserStatusConverter.class)
    @Column(name = "user_status")
    private UserStatus status;
    /**
     * 用户是否为隐藏状态
     */
    @Column(name = "user_is_private")
    private Integer isPrivate;
    /**
     * 用户共有多少图片
     */
    @Column(name = "user_image_count")
    private Long imageCount;
    /**
     * 用户共有多少相册
     */
    @Column(name = "user_album_count")
    private Long albumCount;
    /**
     * 用户被赞量
     */
    @Column(name = "user_likes")
    private Long likes;
    /**
     * 用户点赞量
     */
    @Column(name = "user_liked")
    private Long liked;
    /**
     * 用户被关注量
     */
    @Column(name = "user_following")
    private Long following;
    /**
     * 用户关注量
     */
    @Column(name = "user_followers")
    private Long followers;
    /**
     * 用户内容被观看数
     */
    @Column(name = "user_content_views")
    private Long contentViews;
}
