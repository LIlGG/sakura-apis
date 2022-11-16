package com.lixingyong.images.model.param;

import com.lixingyong.common.autoconfigure.param.ServerParam;
import com.lixingyong.images.model.enums.ApiRequestContentType;
import com.lixingyong.images.model.enums.ServiceType;
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
 * 若计划新增对应的 server 或者 type 等，请务必确保在对应的枚举类中注册。这是出于接口范围的安全考虑，防止过量访问
 *
 * @author LIlGG
 * @since 2021/7/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseParam extends ServerParam {
    /**
     * 请求类型
     */
    @EnumValid(message = "不存在 Type 所对应的请求类型", target = ApiRequestContentType.class)
    private String type;

    /**
     * 二级分类，用于寻找服务处理器
     */
    @EnumValid(message = "不存在 iType 所对应的功能", target = ServiceType.class)
    private String iType = ServiceType.IMAGE.getServiceName();
}
