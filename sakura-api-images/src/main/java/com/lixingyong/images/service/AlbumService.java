package com.lixingyong.images.service;

import java.util.List;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
public interface AlbumService<T, DTO> extends ImagesApiService<T, DTO> {
    /**
     * 获取目标用户下的所有公开相册
     *
     * @param userId 用户编号
     * @return 获取到的公开相册信息
     */
    List<DTO> getPublicAlbumsByUserId(Number userId);
}
