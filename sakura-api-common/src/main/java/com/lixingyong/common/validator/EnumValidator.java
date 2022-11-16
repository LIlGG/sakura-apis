package com.lixingyong.common.validator;

import com.lixingyong.common.utils.ValueEnum;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 对值类型枚举中的值进行校验
 *
 * @author LIlGG
 * @since 2021/8/6
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
    /**
     * 枚举校验注解
     */
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Class<? extends ValueEnum<?>> clazz = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();

        // 当值为空并且忽略空值时，不进行校验
        if (value == null && ignoreEmpty) {
            return true;
        }

        // 当传入的类型不是枚举类时，不进行校验
        if (!clazz.isEnum()) {
            return true;
        }

        return Stream.of(clazz.getEnumConstants())
                .anyMatch(item -> item.getValue().equals(value));
    }
}
