package com.lixingyong.images.server.chevereto.converter;

import com.lixingyong.images.server.chevereto.model.enums.StorageMode;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.lixingyong.common.utils.ValueEnum;

/**
 * @author LIlGG
 * @since 2022-10-21
 */
@Converter
public class StorageModeConverter implements AttributeConverter<StorageMode, String> {
    @Override
    public String convertToDatabaseColumn(StorageMode attribute) {
        return attribute.getValue();
    }

    @Override
    public StorageMode convertToEntityAttribute(String dbData) {
        return ValueEnum.valueToEnum(StorageMode.class, dbData);
    }
}
