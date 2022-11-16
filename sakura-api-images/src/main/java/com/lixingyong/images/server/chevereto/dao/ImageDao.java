package com.lixingyong.images.server.chevereto.dao;

import com.lixingyong.images.server.chevereto.model.entity.ImageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author LIlGG
 * @since 2022-10-17
 */
public interface ImageDao extends JpaRepository<ImageEntity, Long>,
    JpaSpecificationExecutor<ImageEntity> {

    /**
     * 获取公共可见的图片，条件如下：
     *
     * <ul>
     *     <li>图片状态为已审阅</li>
     *     <li>图片所属用户状态为已验证</li>
     *     <li>图片不在相册中或图片所属相册为公共可见</li>
     * </ul>
     *
     * @return List<ImageEntity>
     */
    @Query("select image from ImageEntity image where image.isApproved = 1 "
        + "and image.user.status = com.lixingyong.images.server.chevereto.model.enums.UserStatus.VALID " +
        "and (image.album IS NULL OR image.album.privacy = com.lixingyong.images.server.chevereto.model.enums.AlbumPrivacy.PUBLIC) ")
    List<ImageEntity> findPublicImage();
}
