package cn.aegiron.annotation;

import java.lang.annotation.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName Log.java
 * @Description 操作日志注解
 * @since 2026/2/8 16:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作模块
     */
    String title() default "";

    /**
     * 业务类型（0其它 1新增 2修改 3删除 4查询 5导出 6导入）
     */
    int businessType() default 0;

    /**
     * 是否保存请求参数
     */
    boolean saveRequestData() default true;

    /**
     * 是否保存响应参数
     */
    boolean saveResponseData() default true;

}
