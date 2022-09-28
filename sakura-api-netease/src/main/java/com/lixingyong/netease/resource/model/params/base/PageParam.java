package com.lixingyong.netease.resource.model.params.base;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 分页参数
 *
 * @author LIlGG
 * @since 2021/8/7
 */
@Data
@SuperBuilder(toBuilder = true)
public class PageParam {
    /**
     * 搜索每页数量，默认 30
     */
    @Builder.Default
    private int limit = 30;
    /**
     *  偏移数量，用于分页 , 如 ( offset - 1 ) * limit。 , 默认为 0
     */
    @Builder.Default
    private int offset = 0;
}
