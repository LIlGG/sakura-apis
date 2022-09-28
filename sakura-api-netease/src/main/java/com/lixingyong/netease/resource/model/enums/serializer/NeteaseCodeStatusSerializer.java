package com.lixingyong.netease.resource.model.enums.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.*;
import com.lixingyong.netease.resource.model.enums.NeteaseCodeStatus;
import java.lang.reflect.Type;
import utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2021/7/30
 */
public class NeteaseCodeStatusSerializer extends AbstractSerializer {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object uncasedSensitive = IntegerCodec.instance.deserialze(parser, type, fieldName);
        return (T) ValueEnum.valueToEnum(NeteaseCodeStatus.class, uncasedSensitive);
    }
}
