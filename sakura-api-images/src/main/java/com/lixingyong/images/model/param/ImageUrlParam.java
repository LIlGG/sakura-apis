package com.lixingyong.images.model.param;

import com.lixingyong.images.server.chevereto.model.enums.ImageSizeType;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lixingyong.common.validator.EnumValid;

/**
 * @author LIlGG
 * @since 2022-10-21
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImageUrlParam extends IdParam implements ThumbParam {

    @Min(message = "图片大小需大于 0", value = 0)
    private Integer th;

    @EnumValid(message = "图片大小设置不正确", target = ImageSizeType.class)
    private String qn = ImageSizeType.ORIGINAL.getValue();

    @Override
    public Integer getWidth() {
        return this.th;
    }

    @Override
    public Integer getHeight() {
        return null;
    }
}
