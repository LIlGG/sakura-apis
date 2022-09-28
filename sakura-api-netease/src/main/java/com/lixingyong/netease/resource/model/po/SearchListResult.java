package com.lixingyong.netease.resource.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 请求网易云 API 搜索功能返回的列表
 *
 * @author LIlGG
 * @since 2021/8/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchListResult extends BaseNeteaseResult {
    /**
     * 搜索功能返回值
     */
    private String result;
}
