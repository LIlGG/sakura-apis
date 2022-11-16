package com.lixingyong.images.model.param;

import com.lixingyong.images.model.enums.ImageSearchKind;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lixingyong.common.validator.EnumValid;

/**
 * @author LIlGG
 * @since 2022-10-17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImageSearchParam extends IdParam {
    /**
     * 图片检索时的方式
     */
    @EnumValid(message = "图片检索方式不正确", target = ImageSearchKind.class)
    private String kind = ImageSearchKind.RANDOM.getValue();
    /**
     * 获取的图片数量
     */
    @Min(value = 10, message = "图片获取数量需大于 10")
    @Max(value = 100, message = "一次性获取的图片不能大于 100")
    private Integer count = 10;
    /**
     * 图片起始位置
     */
    @PositiveOrZero(message = "起始位置需大于等于 0")
    private Integer start = 0;
}
