package com.lixingyong.images.server.chevereto.model.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * Chevereto 分类实体
 *
 * @author LIlGG
 * @since 2022-10-16
 */
@Table(name = "chv_categories")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CategoryEntity extends BaseEntity {
    /**
     * 分类 ID
     */
    @Id
    @Column(name = "category_id")
    private Long id;
    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String name;
    /**
     * 分类 url key
     */
    @Column(name = "category_url_key")
    private String urlKey;
    /**
     * 分类描述
     */
    @Column(name = "category_description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CategoryEntity that = (CategoryEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
