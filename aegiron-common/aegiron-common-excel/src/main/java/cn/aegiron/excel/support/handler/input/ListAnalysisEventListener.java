package cn.aegiron.excel.support.handler.input;

import cn.aegiron.excel.support.util.ErrorMessage;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName ListAnalysisEventListener.java
 * @Description 使用 EasyExcel 的 AnalysisEventListener 读取EXCEL
 * @since 2026/2/8 17:38
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

    /**
     * 获取 Excel 解析的对象列表
     *
     * @return 解析出的对象列表
     */
    public abstract List<T> getList();

    /**
     * 获取异常校验结果
     *
     * @return 集合
     */
    public abstract List<ErrorMessage> getErrors();
}
