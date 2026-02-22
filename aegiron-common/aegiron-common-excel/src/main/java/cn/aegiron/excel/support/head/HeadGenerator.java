package cn.aegiron.excel.support.head;

/**
 * @author panda
 * @version 1.0
 * @ClassName HeadGenerator.java
 * @Description Excel文件头部处理
 * @since 2026/2/8 17:42
 */
public interface HeadGenerator {

    /**
     * 根据 Class 生成 Excel 头
     *
     * @param clazz 当前 Sheet 的数据类型
     * @return List<List < String>> Head头信息
     */
    HeadMeta head(Class<?> clazz);
}
