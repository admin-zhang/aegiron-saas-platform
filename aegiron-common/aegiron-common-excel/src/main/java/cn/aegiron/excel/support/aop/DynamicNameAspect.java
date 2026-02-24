package cn.aegiron.excel.support.aop;

import cn.aegiron.excel.annotation.ResponseExcel;
import cn.aegiron.excel.support.processor.NameProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author panda
 * @version 1.0
 * @ClassName DynamicNameAspect.java
 * @Description Excel下载时的文件名增强处理
 * @since 2026/2/8 17:53
 */
@Aspect
@RequiredArgsConstructor
public class DynamicNameAspect {

    public static final String EXCEL_NAME_KEY = "__EXCEL_NAME_KEY__";

    private final NameProcessor processor;

    @Before("@annotation(excel)")
    public void around(JoinPoint point, ResponseExcel excel) {
        MethodSignature ms = (MethodSignature) point.getSignature();
        String fileName = excel.fileName();
        if (!StringUtils.hasText(fileName)) {
            // 当导出的 Excel 文件未设置文件名称时，取系统当前时间作为文件名
            fileName = LocalDateTime.now().toString();
        } else {
            // 命名处理器决定下载文件名
            fileName = processor.doDetermineName(point.getArgs(), ms.getMethod(), excel.fileName());
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(requestAttributes).setAttribute(EXCEL_NAME_KEY, fileName, RequestAttributes.SCOPE_REQUEST);
    }
}
