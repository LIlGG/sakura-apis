package com.lixingyong.images.model.param;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 编号类型请求参数
 *
 * <p>编号将会根据查询条件不同而传入不同的参数</p>
 *
 * @author LIlGG
 * @since 2022/10/2
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IdParam extends BaseParam {
    /**
     * 编号
     */
    @NotNull(message = "编号不能为空")
    private String id;
}
