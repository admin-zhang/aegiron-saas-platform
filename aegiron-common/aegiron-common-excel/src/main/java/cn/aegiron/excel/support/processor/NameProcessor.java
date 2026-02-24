package cn.aegiron.excel.support.processor;

import java.lang.reflect.Method;

/**
 * @author panda
 * @version 1.0
 * @ClassName NameProcessor.java
 * @Description Excel导出文件名处理器
 * @since 2026/2/8 17:54
 */
public interface NameProcessor {

    /**
     * 解析 Excel 文件下载时的文件名
     *
     * @param args     被 @ResponseExcel 修饰的方法参数
     * @param method   被 @ResponseExcel 修饰的方法名
     * @param fileName 导出的文件名
     */
    String doDetermineName(Object[] args, Method method, String fileName);
}
