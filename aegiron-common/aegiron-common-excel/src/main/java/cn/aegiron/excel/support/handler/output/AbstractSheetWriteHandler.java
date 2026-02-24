package cn.aegiron.excel.support.handler.output;



import cn.aegiron.excel.annotation.ResponseExcel;
import cn.aegiron.excel.annotation.Sheet;
import cn.aegiron.excel.support.ExcelConfigProperties;
import cn.aegiron.excel.support.aop.DynamicNameAspect;
import cn.aegiron.excel.support.converts.LocalDateStringConverter;
import cn.aegiron.excel.support.converts.LocalDateTimeStringConverter;
import cn.aegiron.excel.support.enhance.WriterBuilderEnhancer;
import cn.aegiron.excel.support.head.HeadGenerator;
import cn.aegiron.excel.support.head.HeadMeta;
import cn.aegiron.excel.support.head.I18nHeaderCellWriteHandler;
import cn.aegiron.excel.support.util.ExcelException;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author panda
 * @version 1.0
 * @ClassName AbstractSheetWriteHandler.java
 * @Description Excel工作表写入处理器
 * @since 2026/2/8 17:51
 */
@RequiredArgsConstructor
public abstract class AbstractSheetWriteHandler implements SheetWriteHandler, ApplicationContextAware {

    /**
     * 自定义的 Excel 模块配置类
     */
    private final ExcelConfigProperties configProperties;

    /**
     * 自定义类型转换器
     */
    private final ObjectProvider<List<Converter<?>>> converterProvider;

    /**
     * Excel 导出写入增强
     */
    private final WriterBuilderEnhancer excelWriterBuilderEnhance;

    private ApplicationContext applicationContext;

    @Getter
    @Setter
    @Autowired(required = false)
    private I18nHeaderCellWriteHandler i18nHeaderCellWriteHandler;

    /**
     * Excel 文件的具体导出逻辑
     *
     * @param data          要导出的数据
     * @param response      HTTP响应
     * @param responseExcel 导出配置的元信息注解
     */
    @Override
    @SneakyThrows(UnsupportedEncodingException.class)
    public void export(Object data, HttpServletResponse response, ResponseExcel responseExcel) {
        // 检查 Sheet 配置
        check(responseExcel);
        // excel 文件名处理
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String name = (String) Objects.requireNonNull(requestAttributes).getAttribute(DynamicNameAspect.EXCEL_NAME_KEY, RequestAttributes.SCOPE_REQUEST);
        if (name == null) {
            name = UUID.randomUUID().toString();
        }
        String fileName = String.format("%s%s", URLEncoder.encode(name, "utf-8"), responseExcel.suffix().getValue());
        // 根据实际的文件类型找到对应的 contentType
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MediaType::toString).orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        // 模版方法
        write(data, response, responseExcel);
    }

    /**
     * 获取 ExcelWriter 对象
     *
     * @param response      HTTP 响应
     * @param responseExcel ResponseExcel 注解
     * @return 用于通过 POI 将值写入 Excel 的 ExcelWriter 对象
     */
    @SneakyThrows(IOException.class)
    public ExcelWriter getExcelWriter(HttpServletResponse response, ResponseExcel responseExcel) {
        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                .registerConverter(LocalDateStringConverter.INSTANCE)
                .registerConverter(LocalDateTimeStringConverter.INSTANCE).autoCloseStream(true)
                .excelType(responseExcel.suffix()).inMemory(responseExcel.inMemory());
        // 配置 excel 文件密码
        if (StringUtils.hasText(responseExcel.password())) {
            writerBuilder.password(responseExcel.password());
        }
        // 自定义 excel 导出字段
        if (responseExcel.include().length != 0) {
            writerBuilder.includeColumnFieldNames(Arrays.asList(responseExcel.include()));
        }
        // 自定义 excel 忽略字段
        if (responseExcel.exclude().length != 0) {
            writerBuilder.excludeColumnFieldNames(Arrays.asList(responseExcel.exclude()));
        }
        // 注册 excel 工作表写入处理器
        if (responseExcel.writeHandler().length != 0) {
            for (Class<? extends WriteHandler> clazz : responseExcel.writeHandler()) {
                writerBuilder.registerWriteHandler(BeanUtils.instantiateClass(clazz));
            }
        }
        // 开启国际化头信息处理
        if (responseExcel.i18nHeader() && i18nHeaderCellWriteHandler != null) {
            writerBuilder.registerWriteHandler(i18nHeaderCellWriteHandler);
        }
        // 自定义注入的转换器
        registerCustomConverter(writerBuilder);
        // 注册转换器
        if (responseExcel.converter().length != 0) {
            for (Class<? extends Converter> clazz : responseExcel.converter()) {
                writerBuilder.registerConverter(BeanUtils.instantiateClass(clazz));
            }
        }
        // 写入模版文件
        String templatePath = configProperties.getTemplatePath();
        if (StringUtils.hasText(responseExcel.template())) {
            ClassPathResource classPathResource = new ClassPathResource(templatePath + File.separator + responseExcel.template());
            InputStream inputStream = classPathResource.getInputStream();
            writerBuilder.withTemplate(inputStream);
        }

        writerBuilder = excelWriterBuilderEnhance.enhanceExcel(writerBuilder, response, responseExcel, templatePath);

        return writerBuilder.build();
    }

    /**
     * 获取 WriteSheet 对象
     *
     * @param sheet                 @Sheet注解元信息
     * @param dataClass             数据类型
     * @param template              模板
     * @param bookHeadEnhancerClass 自定义头处理器
     * @return WriteSheet 对象
     */
    public WriteSheet sheet(Sheet sheet, Class<?> dataClass, String template, Class<? extends HeadGenerator> bookHeadEnhancerClass) {
        // Sheet 编号和名称
        Integer sheetNo = sheet.sheetNo() >= 0 ? sheet.sheetNo() : null;
        String sheetName = sheet.sheetName();
        // 是否模板写入
        ExcelWriterSheetBuilder writerSheetBuilder = StringUtils.hasText(template) ? EasyExcel.writerSheet(sheetNo) : EasyExcel.writerSheet(sheetNo, sheetName);
        // 头信息增强 1. 优先使用 sheet 指定的头信息增强 2. 其次使用 @ResponseExcel 中定义的全局头信息增强
        Class<? extends HeadGenerator> headGenerateClass = null;
        if (isNotInterface(sheet.headGenerateClass())) {
            headGenerateClass = sheet.headGenerateClass();
        } else if (isNotInterface(bookHeadEnhancerClass)) {
            headGenerateClass = bookHeadEnhancerClass;
        }
        // 定义头信息增强则使用其生成头信息，否则使用 dataClass 来自动获取
        if (headGenerateClass != null) {
            fillCustomHeadInfo(dataClass, bookHeadEnhancerClass, writerSheetBuilder);
        } else if (dataClass != null) {
            writerSheetBuilder.head(dataClass);
            if (sheet.excludes().length > 0) {
                writerSheetBuilder.excludeColumnFieldNames(Arrays.asList(sheet.excludes()));
            }
            if (sheet.includes().length > 0) {
                writerSheetBuilder.includeColumnFieldNames(Arrays.asList(sheet.includes()));
            }
        }
        // sheetBuilder 增强
        writerSheetBuilder = excelWriterBuilderEnhance.enhanceSheet(writerSheetBuilder, sheetNo, sheetName, dataClass, template, headGenerateClass);
        return writerSheetBuilder.build();
    }

    /**
     * 自定义注入转换器 如果有需要，子类自己重写
     *
     * @param builder ExcelWriterBuilder
     */
    public void registerCustomConverter(ExcelWriterBuilder builder) {
        converterProvider.ifAvailable(converters -> converters.forEach(builder::registerConverter));
    }

    @Override
    public void check(ResponseExcel responseExcel) {
        if (responseExcel.sheets().length == 0) {
            throw new ExcelException("@ResponseExcel sheet 配置不合法");
        }
    }

    private void fillCustomHeadInfo(Class<?> dataClass, Class<? extends HeadGenerator> headEnhancerClass, ExcelWriterSheetBuilder writerSheetBuilder) {
        HeadGenerator headGenerator = this.applicationContext.getBean(headEnhancerClass);
        Assert.notNull(headGenerator, "The header generated bean does not exist.");
        HeadMeta head = headGenerator.head(dataClass);
        writerSheetBuilder.head(head.getHead());
        writerSheetBuilder.excludeColumnFieldNames(head.getIgnoreHeadFields());
    }

    /**
     * 是否为Null Head Generator
     *
     * @param headGeneratorClass 头生成器类型
     * @return true 已指定 false 未指定(默认值)
     */
    private boolean isNotInterface(Class<? extends HeadGenerator> headGeneratorClass) {
        return !Modifier.isInterface(headGeneratorClass.getModifiers());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
