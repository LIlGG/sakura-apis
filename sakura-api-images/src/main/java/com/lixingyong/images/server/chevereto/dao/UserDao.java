package com.lixingyong.images.server.chevereto.dao;

import com.lixingyong.images.server.chevereto.model.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author LIlGG
 * @since 2022-10-17
 */
public interface UserDao extends JpaRepository<UserEntity, Long>,
    JpaSpecificationExecutor<UserEntity> {

    /**
     * 检索满足以下条件的用户。
     * <ul>
     *     <li>用户处于 <span>valid（已验证）</span> 状态</li>
     *     <li>用户处于 <span>非隐身</span> 状态</li>
     * </ul>
     *
     * @return UserEntity
     */
    @Query("select user from UserEntity user where " +
        "user.status <> com.lixingyong.images.server.chevereto.model.enums.UserStatus.VALID " +
        "and user.isPrivate <> 0")
    List<UserEntity> findPublicUser();
}
