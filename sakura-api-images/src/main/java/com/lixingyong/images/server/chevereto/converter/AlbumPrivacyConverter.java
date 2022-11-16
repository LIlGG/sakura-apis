package com.lixingyong.images.server.chevereto.converter;

import com.lixingyong.images.server.chevereto.model.enums.AlbumPrivacy;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.lixingyong.common.utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2022-10-21
 */
@Converter
public class AlbumPrivacyConverter implements AttributeConverter<AlbumPrivacy, String> {
    @Override
    public String convertToDatabaseColumn(AlbumPrivacy attribute) {
        return attribute.getValue();
    }

    @Override
    public AlbumPrivacy convertToEntityAttribute(String dbData) {
        return ValueEnum.valueToEnum(AlbumPrivacy.class, dbData);
    }
}
