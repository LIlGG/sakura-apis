package com.lixingyong.images.server.chevereto.model.dto.base;
import lombok.Data;

/**
 * @author LIlGG
 * @since 2020/11/27
 */
@Data
public class BaseUserDTO implements BaseDTO {
    /**
     * 用户昵称（可能为空）
     */
    private String name;
    /**
     * 经过处理后的用户名（可以用来查询信息）
     */
    private String username;
    /**
     * 用户 url
     */
    private String url;
    /**
     * 用户共有多少图片
     */
    private Long imageCount;
    /**
     * 用户共有多少相册
     */
    private Long albumCount;
}
