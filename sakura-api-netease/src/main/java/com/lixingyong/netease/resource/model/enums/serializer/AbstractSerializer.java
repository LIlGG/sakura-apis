package com.lixingyong.netease.resource.model.enums.serializer;

import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.*;
import java.io.IOException;
import java.lang.reflect.Type;
import utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2021/8/3
 */
public abstract class AbstractSerializer implements ObjectSerializer, ObjectDeserializer {

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        IntegerCodec.instance.write(serializer, ((ValueEnum<?>) object).getValue(), fieldName, fieldType, features);
    }
}
