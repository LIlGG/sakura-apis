package com.lixingyong.netease.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author LIlGG
 * @since 2022-09-27
 */
public class LoginEvent extends ApplicationEvent {
    private Long id;

    private String message;

    public LoginEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
