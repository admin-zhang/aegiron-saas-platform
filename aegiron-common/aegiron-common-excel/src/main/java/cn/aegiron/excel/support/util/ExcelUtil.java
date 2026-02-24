package cn.aegiron.excel.support.util;

import cn.aegiron.excel.support.listener.EasyExcelListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panda
 * @version 1.0
 * @ClassName ExcelUtil.java
 * @Description Excel工具类
 * @since 2026/2/8 18:10
 */
public class ExcelUtil {

    /**
     * 动态表头导入功能
     *
     * @param file 文件
     * @return
     */
    public static List<Map<String, String>> importExcel(MultipartFile file) throws Exception {
        try {
            // 首先校验传入文件是否为空
            if (file == null) {
                throw new Exception("传入数据为空");
            }
            // 引入监听器（此处需注意，监听器不可被Spring管理）
            EasyExcelListener readListener = new EasyExcelListener();
            // 开始处理excel
            EasyExcelFactory.read(file.getInputStream(), readListener)
                    .sheet(0)
                    .doRead();
            // 获取表头（验空）
            List<Map<Integer, String>> headList = readListener.getHeadList();
            if (CollectionUtils.isEmpty(headList)) {
                throw new RuntimeException("Excel表头不能为空");
            }
            // 获取表数据(验空)
            List<Map<Integer, String>> dataList = readListener.getDataList();
            if (CollectionUtils.isEmpty(dataList)) {
                throw new RuntimeException("Excel数据内容不能为空");
            }
            //获取头部,取最后一次解析的列头数据
            Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() - 1);
            //封装数据体
            List<Map<String, String>> excelDataList = new ArrayList<Map<String, String>>();
            for (Map<Integer, String> dataRow : dataList) {
                HashMap<String, String> rowData = new HashMap<>();
                excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                    rowData.put(columnHead.getValue(), dataRow.get(columnHead.getKey()));
                });
                excelDataList.add(rowData);
            }
            return excelDataList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("导入失败");
        }
    }

    /**
     * 动态表头导出
     *
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param headers   Excel表头信息
     * @param data     Excel数据
     */
    public static void exportExcel(HttpServletResponse response, String fileName, List<List<String>> headers, List<List<Object>> data) {
        try {
            //1 设置下载相关内容
            //设置mime类型
            response.setContentType("application/vnd.ms-excel");
            //设置编码
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //设置响应头信息 Content-disposition
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(headers)
                    .sheet(fileName)
                    .doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 有实体的导出
     *
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param data     数据
     * @param clazz    实体类型
     * @param <T>
     */
    public static <T> void exportExcelByEntity(HttpServletResponse response, String fileName, List<T> data, Class<T> clazz) {
        try {
            //1 设置下载相关内容
            //设置mime类型
            response.setContentType("application/vnd.ms-excel");
            //设置编码
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //设置响应头信息 Content-disposition
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 导出
            EasyExcel.write(response.getOutputStream(), clazz)
                    .sheet(fileName)
                    .doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
