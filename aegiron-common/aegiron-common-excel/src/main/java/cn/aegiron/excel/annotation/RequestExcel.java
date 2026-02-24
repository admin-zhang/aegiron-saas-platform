package cn.aegiron.excel.annotation;

import cn.aegiron.excel.support.handler.input.DefaultAnalysisEventListener;
import cn.aegiron.excel.support.handler.input.ListAnalysisEventListener;

import java.lang.annotation.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName RequestExcel.java
 * @Description 导入 Excel 注解(封装导入过程中的元数据)
 * @since 2026/2/8 17:35
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {
    /**
     * 上传的 Excel 文件的名字，默认为 file
     */
    String fileName() default "file";

    /**
     * 读取 Excel 的监听器类
     */
    Class<? extends ListAnalysisEventListener<?>> readListener() default DefaultAnalysisEventListener.class;

    /**
     * 是否跳过读取空行
     *
     * @return 默认跳过
     */
    boolean ignoreEmptyRow() default true;
}
