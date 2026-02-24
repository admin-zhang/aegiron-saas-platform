package cn.aegiron.excel.support.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * @author panda
 * @version 1.0
 * @ClassName Validators.java
 * @Description 校验工具
 * @since 2026/2/8 17:40
 */
public final class Validators {

    private Validators() {
    }

    private static final Validator VALIDATOR;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 校验对象上的所有约束条件
     *
     * @param object 待校验的对象
     * @param <T>    待校验对象的类型
     * @return 对象违背约束的集合，没有则为空
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return VALIDATOR.validate(object);
    }
}
