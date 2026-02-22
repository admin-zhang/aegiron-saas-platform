package cn.aegiron.excel.support.handler.input;

import cn.aegiron.excel.annotation.ExcelLine;
import cn.aegiron.excel.support.util.ErrorMessage;
import cn.aegiron.excel.support.util.Validators;
import com.alibaba.excel.context.AnalysisContext;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author panda
 * @version 1.0
 * @ClassName DefaultAnalysisEventListener.java
 * @Description 默认读取上传 Excel 的监听器
 * @since 2026/2/8 17:37
 */
@Slf4j
public class DefaultAnalysisEventListener extends ListAnalysisEventListener<Object> {
    private final List<Object> list = new ArrayList<>();

    private final List<ErrorMessage> errorMessageList = new ArrayList<>();

    private Long lineNum = 1L;

    /**
     * 执行读 Excel 逻辑
     *
     * @param o               对象
     * @param analysisContext Excel 阅读器的主要锚定点
     */
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("开始读取 Excel");
        lineNum++;
        Set<ConstraintViolation<Object>> violations = Validators.validate(o);
        if (!violations.isEmpty()) {
            Set<String> messageSet = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            errorMessageList.add(new ErrorMessage(lineNum, messageSet));
        } else {
            // 获取当前类的所有字段，不包括继承来的
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelLine.class) && field.getType() == Long.class) {
                    try {
                        field.setAccessible(true);
                        field.set(o, lineNum);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            list.add(o);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.debug("Excel read analysed");
    }

    @Override
    public List<Object> getList() {
        return list;
    }

    @Override
    public List<ErrorMessage> getErrors() {
        return errorMessageList;
    }
}
