package cn.aegiron.excel.annotation;

import java.lang.annotation.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName ExcelLine.java
 * @Description excel行注解
 * @since 2026/2/8 17:34
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelLine {
}
