package cn.aegiron.excel.support.enhance;

import cn.aegiron.excel.annotation.ResponseExcel;
import cn.aegiron.excel.support.head.HeadGenerator;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author panda
 * @version 1.0
 * @ClassName DefaultWriterBuilderEnhancer.java
 * @Description 默认数据写入增强
 * @since 2026/2/8 18:01
 */
public class DefaultWriterBuilderEnhancer implements WriterBuilderEnhancer {

    /**
     * ExcelWriterBuilder 增强
     *
     * @param writerBuilder ExcelWriterBuilder
     * @param response      HttpServletResponse
     * @param responseExcel ResponseExcel
     * @param templatePath  模板路径
     * @return ExcelWriterBuilder
     */
    @Override
    public ExcelWriterBuilder enhanceExcel(ExcelWriterBuilder writerBuilder, HttpServletResponse response, ResponseExcel responseExcel, String templatePath) {
        // doNothing
        return writerBuilder;
    }

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
    @Override
    public ExcelWriterSheetBuilder enhanceSheet(ExcelWriterSheetBuilder writerSheetBuilder, Integer sheetNo, String sheetName, Class<?> dataClass, String template, Class<? extends HeadGenerator> headEnhancerClass) {
        // doNothing
        return writerSheetBuilder;
    }
}
