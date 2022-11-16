package com.lixingyong.music.model.param;

import com.lixingyong.common.autoconfigure.param.ServerParam;
import com.lixingyong.music.model.enums.ApiRequestContentType;
import com.lixingyong.music.model.enums.FormatProcessType;
import lombok.Data;
import com.lixingyong.common.validator.EnumValid;
import lombok.EqualsAndHashCode;

/**
 * 请求参数超类
 *
 * <p>
 * 此类中的属性均为可选参数，但若传入了目标参数，则参数需要控制在对应的枚举内。
 * </p>
 *
 * 若计划新增对应的 server 或者 type，请务必确保在对应的枚举类中注册。这是出于接口范围的安全考虑，防止过量访问
 *
 * @see ApiRequestContentType
 * @see FormatProcessType
 *
 * @author LIlGG
 * @since 2021/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseParam extends ServerParam {
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
