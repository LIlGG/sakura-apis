package com.lixingyong.common.autoconfigure.param;

import lombok.Data;

/**
 * @author LIlGG
 * @since 2022-10-27
 */
@Data
public abstract class ServerParam {
    /**
     * 执行具体处理功能的服务
     */
    private String server;
}
