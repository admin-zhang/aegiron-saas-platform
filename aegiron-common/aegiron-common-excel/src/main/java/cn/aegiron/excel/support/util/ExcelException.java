package cn.aegiron.excel.support.util;

import java.io.Serial;

/**
 * @author panda
 * @version 1.0
 * @ClassName ExcelException.java
 * @Description Excel异常类
 * @since 2026/2/8 18:06
 */
public class ExcelException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExcelException(String message) {
        super(message);
    }
}
