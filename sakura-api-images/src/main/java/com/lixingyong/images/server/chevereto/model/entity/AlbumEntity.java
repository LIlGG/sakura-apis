package com.lixingyong.images.server.chevereto.model.entity;

import com.lixingyong.images.server.chevereto.converter.AlbumPrivacyConverter;
import com.lixingyong.images.server.chevereto.model.enums.AlbumPrivacy;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * chevereto 相册映射实体类（不包括全部信息）
 * 只查询公共相册，私有相册不支持查询
 *
 * @author LIlGG
 * @since 2022-10-16
 */
@Entity
@Table(name = "chv_albums")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AlbumEntity extends BaseEntity {
    /**
     * 相册 ID
     */
    @Id
    @Column(name = "album_id")
    private Long id;
    /**
     * 相册 编号
     */
    @Transient
    private String code;
    /**
     * 相册名称
     */
    @Column(name = "album_name")
    private String name;
    /**
     * 相册所属用户ID
     */
    @OneToOne
    @JoinColumn(name = "album_user_id")
    private UserEntity user;
    /**
     * 相册创建日期
     */
    @Column(name = "album_date")
    private Date date;
    /**
     * 相册所包含的图片数量
     */
    @Column(name = "album_image_count")
    private Long imageCount;
    /**
     * 相册详细说明
     */
    @Column(name = "album_description", columnDefinition = "mediumtext")
    private String description;
    /**
     * 相册点赞数
     */
    @Column(name = "album_likes")
    private Long likes;
    /**
     * 相册查看量
     */
    @Column(name = "album_views")
    private Long views;
    /**
     * 相册可见度
     */
    @Convert(converter = AlbumPrivacyConverter.class)
    @Column(name = "album_privacy")
    private AlbumPrivacy privacy;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AlbumEntity that = (AlbumEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
