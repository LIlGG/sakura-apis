package com.lixingyong.music.server.netease.resource.model.enums;

import utils.ValueEnum;

/**
 * 网易云音乐请求状态
 *
 * @author LIlGG
 * @since 2021/7/29
 */
public enum NeteaseCodeStatus implements ValueEnum<Integer> {
    /**
     * 请求正常
     */
    OK(200, "正常"),
    /**
     * 资源不可用
     */
    BAD_REQUEST(400, "请求错误"),
    /**
     * 资源不可用
     */
    FORBIDDEN(403, "资源不可用"),
    /**
     * 资源不可用
     */
    PASSWORD_ERROR(502, "密码错误"),
    /**
     * 未登录
     */
    NOT_LOGIN(20001, "登录后才可查看");


    /**
     * 状态码
     */
    final int code;
    /**
     * 状态中文描述
     */
    final String msg;

    NeteaseCodeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
