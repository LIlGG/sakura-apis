package com.lixingyong.music.model.param;

import javax.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 带有分页数据的基础请求参数
 *
 * @author LIlGG
 * @since 2021/8/7
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BasePageParam extends BaseParam {
    /**
     * 每页数量
     */
    @Min(value = 20, message = "每页数量需要大于等于 20")
    private int limit = 30;
    /**
     * 偏移数量 , 用于分页 , 如 :( 页数 - 1 ) * limit
     */
    @Min(value = 0, message = "偏移量需要大于等于 0")
    private int offset = 0;
}
