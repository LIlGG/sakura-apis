package com.lixingyong.images.service;

import com.lixingyong.images.utils.ImagesApiException;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
public interface ImageService<T, DTO> extends ImagesApiService<T, DTO> {

    /**
     * 获取图片真实链接地址
     *
     * @param id 根据不同的渠道，参数也不同
     * @return 获取到的图片真实地址
     */
    default String getImageUrl(String id) {
        throw new ImagesApiException("该服务不支持此接口");
    }
}
