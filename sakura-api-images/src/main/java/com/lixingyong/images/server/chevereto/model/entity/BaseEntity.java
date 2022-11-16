package com.lixingyong.images.server.chevereto.model.entity;

import javax.persistence.Transient;
import lombok.Data;

/**
 * @author LIlGG
 * @since 2022-10-16
 */
@Data
public class BaseEntity {
    /**
     * 地址
     */
    @Transient
    private String url;
}
