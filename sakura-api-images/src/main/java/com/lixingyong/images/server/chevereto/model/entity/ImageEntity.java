package com.lixingyong.images.server.chevereto.model.entity;

import com.lixingyong.images.server.chevereto.converter.StorageModeConverter;
import com.lixingyong.images.server.chevereto.model.enums.StorageMode;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * Chevereto 映射的图片实体（仅有基础信息）
 * 增加一个 chevereto 不存在的 url 变量，此变量通过目标图片的 storage_id 读取
 *
 * @author LIlGG
 * @since 2022-10-16
 */
@Table(name = "chv_images")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ImageEntity extends BaseEntity {
    /**
     * 图片唯一 ID
     */
    @Id
    @Column(name = "image_id")
    private Long id;
    /**
     * 图片编号
     */
    @Transient
    private String code;
    /**
     * 图片名称
     */
    @Column(name = "image_name")
    private String name;
    /**
     * 中等图片链接
     */
    @Transient
    private String mediumUrl;
    /**
     * 缩略图图片链接
     */
    @Transient
    private String thumbnailUrl;
    /**
     * 图片后缀类型
     */
    @Column(name = "image_extension")
    private String extension;
    /**
     * 图片大小（字节）
     */
    @Column(name = "image_size")
    private int size;
    /**
     * 图片宽度
     */
    @Column(name = "image_width")
    private int width;
    /**
     * 图片高度
     */
    @Column(name = "image_height")
    private int height;
    /**
     * 图片上传日期
     */
    @Column(name = "image_date")
    private Date date;
    /**
     * 图片标题
     */
    @Column(name = "image_title")
    private String title;
    /**
     * 图片详细描述
     */
    @Column(name = "image_description")
    private String description;
    /**
     * 图片所属用户名
     */
    @OneToOne
    @JoinColumn(name = "image_user_id")
    private UserEntity user;
    /**
     * 图片所属相册
     */
    @OneToOne
    @JoinColumn(name = "image_album_id")
    private AlbumEntity album;
    /**
     * 图片保存类型
     */
    @Convert(converter = StorageModeConverter.class)
    @Column(name = "image_storage_mode")
    private StorageMode storageMode;
    /**
     * 图片保存服务类型ID
     * 如果没有就是本地
     */
    @OneToOne
    @JoinColumn(name = "image_storage_id")
    private StoragesEntity storage;
    /**
     * 图片观看量
     */
    @Column(name = "image_views")
    private Long views;
    /**
     * 图片所属分类
     */
    @OneToOne
    @JoinColumn(name = "image_category_id")
    private CategoryEntity category;
    /**
     * 图片被点赞量
     */
    @Column(name = "image_likes")
    private Long likes;
    /**
     * 图片审阅状态
     */
    @Column(name = "image_is_approved")
    private int isApproved;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ImageEntity that = (ImageEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
