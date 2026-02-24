package cn.aegiron.excel.annotation;

import cn.aegiron.excel.support.head.HeadGenerator;

import java.lang.annotation.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName Sheet.java
 * @Description 工作表注解
 * @since 2026/2/8 17:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sheet {

    int sheetNo() default -1;

    /**
     * sheet name
     */
    String sheetName();

    /**
     * 包含字段
     */
    String[] includes() default {};

    /**
     * 排除字段
     */
    String[] excludes() default {};

    /**
     * 头生成器
     */
    Class<? extends HeadGenerator> headGenerateClass() default HeadGenerator.class;
}
