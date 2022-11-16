package com.lixingyong.images.utils;

import com.lixingyong.common.exception.ApiException;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
public class ImagesApiException extends ApiException {

    public ImagesApiException() {
        super();
    }

    public ImagesApiException(String message) {
        super(message);
    }
}
