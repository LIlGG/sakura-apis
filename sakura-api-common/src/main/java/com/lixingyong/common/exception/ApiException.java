package com.lixingyong.common.exception;

/**
 * @author LIlGG
 * @since 2022-10-02
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
