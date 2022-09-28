package com.lixingyong.netease.resource.model.entity.music;

import java.io.Serializable;
import lombok.Data;

/**
 * 音乐详细信息，包括链接等
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
public class Music implements Serializable {
    /**
     * 音乐编号
     */
    private Long id;
    /**
     * 音乐链接
     */
    private String url;
    /**
     * 音乐类型
     */
    private String type;
}
