package com.lixingyong.music.model.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 编号类型请求参数
 *
 * @author LIlGG
 * @since 2022/9/27
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IdParam extends BaseParam {
    /**
     * 歌单编号
     */
    @Min(value = 0, message = "编号不合法")
    @NotNull(message = "编号不能为空")
    private Long id;
}
