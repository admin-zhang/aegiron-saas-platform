package cn.aegiron.excel.support.enhance;

import cn.aegiron.excel.annotation.ResponseExcel;
import cn.aegiron.excel.support.head.HeadGenerator;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author panda
 * @version 1.0
 * @ClassName WriterBuilderEnhancer.java
 * @Description ExcelWriterBuilder增强器
 * @since 2026/2/8 17:49
 */
public interface WriterBuilderEnhancer {

    /**
     * ExcelWriterBuilder 增强
     *
     * @param writerBuilder ExcelWriterBuilder
     * @param response      HttpServletResponse
     * @param responseExcel ResponseExcel
     * @param templatePath  模板路径
     * @return ExcelWriterBuilder
     */
    ExcelWriterBuilder enhanceExcel(ExcelWriterBuilder writerBuilder, HttpServletResponse response, ResponseExcel responseExcel, String templatePath);

    /**
     * ExcelWriterSheetBuilder 增强
     *
     * @param writerSheetBuilder ExcelWriterSheetBuilder
     * @param sheetNo            sheet角标
     * @param sheetName          sheet名，有模板时为空
     * @param dataClass          当前写入的数据所属类
     * @param template           模板文件
     * @param headEnhancerClass  当前指定的自定义头处理器
     * @return ExcelWriterSheetBuilder
     */
    ExcelWriterSheetBuilder enhanceSheet(ExcelWriterSheetBuilder writerSheetBuilder, Integer sheetNo, String sheetName, Class<?> dataClass, String template, Class<? extends HeadGenerator> headEnhancerClass);
}
