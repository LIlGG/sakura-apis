package com.lixingyong.music.server.netease.resource.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.lixingyong.music.server.netease.resource.model.enums.NeteaseCodeStatus;
import com.lixingyong.music.server.netease.resource.model.enums.serializer.NeteaseCodeStatusSerializer;
import lombok.Data;

/**
 * @author LIlGG
 * @since 2021/7/29
 */
@Data
public abstract class BaseNeteaseResult {
    /**
     * 请求状态
     */
    @JSONField(serializeUsing = NeteaseCodeStatusSerializer.class, deserializeUsing = NeteaseCodeStatusSerializer.class)
    private NeteaseCodeStatus code;

    private String msg;

    private String message;
}
