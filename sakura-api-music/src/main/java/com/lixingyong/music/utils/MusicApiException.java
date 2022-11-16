package com.lixingyong.music.utils;

import com.lixingyong.common.exception.ApiException;

/**
 * @author LIlGG
 * @since 2022-09-28
 */
public class MusicApiException extends ApiException {

    public MusicApiException() {
        super();
    }

    public MusicApiException(String message) {
        super(message);
    }
}
