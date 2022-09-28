package com.lixingyong.netease.model.enums;

import com.lixingyong.netease.process.APlayerFormatProcess;
import com.lixingyong.netease.process.FormatProcess;
import utils.ValueEnum;

/**
 * 歌曲返回值格式处理器类型
 *
 * @author LIlGG
 * @since 2021/7/28
 */
public enum FormatProcessType implements ValueEnum<String> {

    /**
     * aplayer
     */
    A_PLAYER("aplayer", APlayerFormatProcess.class);

    /**
     * 请求类型
     */
    final String type;

    /**
     * 格式处理器
     */
    final Class<? extends FormatProcess> aClass;

    FormatProcessType(String type, Class<? extends FormatProcess> aClass) {
        this.type = type;
        this.aClass = aClass;
    }

    public String getType() {
        return type;
    }

    public Class<? extends FormatProcess> getAClass() {
        return aClass;
    }

    @Override
    public String getValue() {
        return type;
    }
}
