package com.lixingyong.images.server.chevereto.converter;

import com.lixingyong.images.server.chevereto.model.enums.UserStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.lixingyong.common.utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2022-10-21
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        return ValueEnum.valueToEnum(UserStatus.class, dbData);
    }
}
