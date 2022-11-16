package com.lixingyong.images.server.chevereto.model.enums;

import com.lixingyong.common.utils.ValueEnum;

/**
 * 文件保存类型
 *
 * @author LIlGG
 * @date 2020/11/25
 */
public enum StorageMode implements ValueEnum<String> {
    /**
     * 日期格式文件，例如 https:xxx/y/m/d/name.xxx
     */
    DATE_FOLDER("datefolder"),
    /**
     * 原生，如 https:xxx/name.xxx
     */
    DIRECT("direct"),
    /**
     * 旧的？？未知
     */
    OLD("old"),
    /**
     * 路径？？未知
     */
    PATH("path");

    private final String value;

    StorageMode(String value){
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
