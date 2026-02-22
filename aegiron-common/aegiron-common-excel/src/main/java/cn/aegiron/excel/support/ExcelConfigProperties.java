package cn.aegiron.excel.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author panda
 * @version 1.0
 * @ClassName ExcelConfigProperties.java
 * @Description Excel配置属性
 * @since 2026/2/8 17:46
 */
@Data
@ConfigurationProperties(prefix = ExcelConfigProperties.PREFIX)
public class ExcelConfigProperties {

    static final String PREFIX = "excel";

    /**
     * 模板路径
     */
    private String templatePath = "excel";
}
