package com.lixingyong.images.server.chevereto.dao;

import com.lixingyong.images.server.chevereto.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIlGG
 * @since 2022-10-17
 */
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {
}
