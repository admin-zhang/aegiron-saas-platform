package cn.aegiron.excel.support.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author panda
 * @version 1.0
 * @ClassName EasyExcelListener.java
 * @Description
 * @since 2026/2/8 18:08
 */
public class EasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 存储全部表头数据
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();

    /**
     * 存储数据体数据
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    @Override//这里会一行行的返回头
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //存储全部表头数据
        System.out.println("1");
        headList.add(headMap);
    }

    @Override// 处理每一行数据
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override// 全部处理结束执行
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public List<Map<Integer, String>> getHeadList() {
        return headList;
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
    }
}
