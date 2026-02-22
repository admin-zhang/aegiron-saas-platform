package cn.aegiron.excel.support.head;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author panda
 * @version 1.0
 * @ClassName HeadMeta.java
 * @Description 表头元数据
 * @since 2026/2/8 17:43
 */
@Data
public class HeadMeta {
    /**
     *
     */
    private List<List<String>> head;

    /**
     * 忽略头对应字段名称
     */
    private Set<String> ignoreHeadFields;
}
