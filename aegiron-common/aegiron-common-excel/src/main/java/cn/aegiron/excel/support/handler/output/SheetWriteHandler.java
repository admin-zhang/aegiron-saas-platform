package cn.aegiron.excel.support.handler.output;

import cn.aegiron.excel.annotation.ResponseExcel;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author panda
 * @version 1.0
 * @ClassName SheetWriteHandler.java
 * @Description Excel(工作簿)的工作表写入处理器
 * @since 2026/2/8 17:55
 */
public interface SheetWriteHandler {

    /**
     * 当前处理器是否能够处理传入的数据
     *
     * @param data 要写入的工作表
     * @return true-支持/false-不支持
     */
    boolean support(Object data);

    /**
     * 校验 responseExcel 的元信息配置
     *
     * @param responseExcel 导出 Excel 逻辑元信息注解
     */
    void check(ResponseExcel responseExcel);

    /**
     * 导出 Excel 文件
     *
     * @param data          要导出的数据
     * @param response      输出对象
     * @param responseExcel 导出 Excel 逻辑元信息注解
     */
    void export(Object data, HttpServletResponse response, ResponseExcel responseExcel);

    /**
     * 填充数据到工作表(由具体处理器实现)
     *
     * @param data          待填充的数据
     * @param response      输出对象
     * @param responseExcel 注解
     */
    void write(Object data, HttpServletResponse response, ResponseExcel responseExcel);
}
