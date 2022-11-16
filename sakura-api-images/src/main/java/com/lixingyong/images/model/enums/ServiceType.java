package com.lixingyong.images.model.enums;

import com.lixingyong.images.service.AlbumService;
import com.lixingyong.images.service.CategoryService;
import com.lixingyong.images.service.ImageService;
import com.lixingyong.images.service.ImagesApiService;
import com.lixingyong.images.service.UserService;
import com.lixingyong.common.utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
public enum ServiceType implements ValueEnum<String> {
    /**
     * 图片
     */
    IMAGE("image", ImageService.class),

    /**
     * 用户
     */
    USER("user", UserService.class),

    /**
     * 相册
     */
    ALBUM("album", AlbumService.class),

    /**
     * 分类
     */
    CATEGORY("category",CategoryService.class);

    final String serviceName;

    final Class<? extends ImagesApiService> serviceClass;

    ServiceType(String serviceName, Class<? extends ImagesApiService> serviceClass) {
        this.serviceName = serviceName;
        this.serviceClass = serviceClass;
    }

    @Override
    public String getValue() {
        return serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Class<? extends ImagesApiService> getServiceClass() {
        return serviceClass;
    }
}
