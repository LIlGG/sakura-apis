package com.lixingyong.netease.event;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import utils.HttpRequestUtil;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
@Component
@ConditionalOnBean(name = "neteaseHttpRequest")
public class LoginListener {

    private final HttpRequestUtil httpRequestUtil;

    public LoginListener(HttpRequestUtil neteaseHttpRequest) {
        httpRequestUtil = neteaseHttpRequest;
    }

    @EventListener
    public void onApplicationEvent(LoginEvent event) {

    }
}
