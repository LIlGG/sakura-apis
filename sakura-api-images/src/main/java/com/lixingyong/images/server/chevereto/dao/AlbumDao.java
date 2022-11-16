package com.lixingyong.images.server.chevereto.dao;

import com.lixingyong.images.server.chevereto.model.entity.AlbumEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author LIlGG
 * @since 2022-10-16
 */
public interface AlbumDao extends JpaRepository<AlbumEntity, Long>,
    JpaSpecificationExecutor<AlbumEntity> {

    /**
     * 检索满足以下条件的相册。
     * <ul>
     *     <li>相册处于公开状态</li>
     *     <li>相册所属用户处于 <span>valid（已验证）</span> 状态</li>
     *     <li>相册所属用户处于 <span>非隐身</span> 状态</li>
     * </ul>
     *
     * @return AlbumEntity
     */
    @Query("select album from AlbumEntity album " +
        "where album.privacy = com.lixingyong.images.server.chevereto.model.enums.AlbumPrivacy.PUBLIC " +
        "and album.user.status = com.lixingyong.images.server.chevereto.model.enums.UserStatus.VALID " +
        "and album.user.isPrivate = 0")
    List<AlbumEntity> findPublicAlbum();
}
