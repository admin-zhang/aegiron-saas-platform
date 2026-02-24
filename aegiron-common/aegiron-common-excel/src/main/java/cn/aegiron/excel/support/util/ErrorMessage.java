package cn.aegiron.excel.support.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author panda
 * @version 1.0
 * @ClassName ErrorMessage.java
 * @Description 错误信息
 * @since 2026/2/8 17:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 错误信息
     */
    private Set<String> errors = new HashSet<>();

    public ErrorMessage(Set<String> errors) {
        this.errors = errors;
    }

    public ErrorMessage(String error) {
        HashSet<String> objects = new HashSet<>();
        objects.add(error);
        this.errors = objects;
    }
}
