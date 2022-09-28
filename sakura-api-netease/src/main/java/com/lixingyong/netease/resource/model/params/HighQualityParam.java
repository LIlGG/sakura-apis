package com.lixingyong.netease.resource.model.params;

import lombok.Builder;
import lombok.Data;

/**
 * 精品歌单参数
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Builder
@Data
public class HighQualityParam {
    /**
     * 歌单标签
     *
     * 歌单标签可以使用 /playlist/highquality/tags 来查找
     */
    private String cat;
    /**
     * 每页数量，默认 20
     */
    @Builder.Default
    private int limit = 20;
    /**
     * 分页参数
     *
     * 取上一页最后一个歌单的 updateTime 获取下一页数据
     */
    private long before;
}
