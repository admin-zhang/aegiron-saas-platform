package cn.aegiron.excel.annotation;

import cn.aegiron.excel.support.head.HeadGenerator;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

import java.lang.annotation.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName ResponseExcel.java
 * @Description 导出 Excel 注解(封装导出过程中的元数据)
 * @since 2026/2/8 17:42
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

    /**
     * Excel 文件下载名称
     */
    String fileName() default "";

    /**
     * Excel 文件类型(xlsx xls)
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;

    /**
     * 文件密码
     */
    String password() default "";

    /**
     * Sheet 名称，支持多个
     */
    Sheet[] sheets() default @Sheet(sheetName = "sheet1");

    /**
     * 在内存中构建 Excel。默认为false(创建缓存文件并最终写入 Excel)
     */
    boolean inMemory() default false;

    /**
     * excel 模板
     */
    String template() default "";

    /**
     * 包含字段
     */
    String[] include() default {};

    /**
     * 排除字段
     */
    String[] exclude() default {};

    /**
     * 拦截器，自定义样式等处理器
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 转换器
     */
    Class<? extends Converter>[] converter() default {};

    /**
     * 自定义Excel头生成器
     */
    Class<? extends HeadGenerator> headGenerator() default HeadGenerator.class;

    /**
     * excel 头信息国际化
     */
    boolean i18nHeader() default false;

    /**
     * 填充模式
     */
    boolean fill() default false;
}
