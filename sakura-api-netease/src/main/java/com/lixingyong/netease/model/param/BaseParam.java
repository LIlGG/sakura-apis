package com.lixingyong.netease.model.param;

import com.lixingyong.netease.model.enums.ApiRequestContentType;
import com.lixingyong.netease.model.enums.FormatProcessType;
import lombok.Data;
import validator.EnumValid;

/**
 * 请求参数超类
 *
 * <p>
 * {@code type} 与 {@code rtype} 都是可选内容，但若传入了目标参数，
 * 则参数需要控制在 {@link ApiRequestContentType} 及 {@link FormatProcessType} 内
 * </p>
 *
 * @see ApiRequestContentType
 * @see FormatProcessType
 *
 * @author LIlGG
 * @since 2021/7/28
 */
@Data
public abstract class BaseParam {

    /**
     * 请求类型
     */
    @EnumValid(message = "不存在 Type 所对应的请求类型", target = ApiRequestContentType.class)
    private String type;

    /**
     * 返回处理器类型，默认为 aplayer
     */
    @EnumValid(message = "不存在 rType 所对应的处理器", target = FormatProcessType.class)
    private String rType = FormatProcessType.A_PLAYER.getType();
}
