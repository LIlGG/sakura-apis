package validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.springframework.core.annotation.AliasFor;
import utils.ValueEnum;

/**
 * 枚举类校验
 * 用于校验参数中的值是否包含在枚举值内。
 * 否则不进行校验
 *
 * @see ValueEnum
 * @author LIlGG
 * @since 2021/8/6
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EnumValidator.class })
public @interface EnumValid {

    /**
     * 校验不通过提示信息
     */
    String message() default "请检查参数类型是否正确";

    /**
     * 分组 作用参考 @Validated 和 @Valid 的区别
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 需校验目标枚举类
     */
    @AliasFor("value")
    Class<? extends ValueEnum<?>> target();

    /**
     * 是否忽略空值
     */
    boolean ignoreEmpty() default true;
}
