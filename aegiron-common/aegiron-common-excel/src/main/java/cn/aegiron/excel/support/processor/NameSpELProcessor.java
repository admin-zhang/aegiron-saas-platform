package cn.aegiron.excel.support.processor;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

/**
 * @author panda
 * @version 1.0
 * @ClassName NameSpELProcessor.java
 * @Description 支持 SpEl 的 Excel 导出文件名处理器
 * @since 2026/2/8 18:08
 */
public class NameSpELProcessor implements NameProcessor{

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * Express 语法解析器
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public String doDetermineName(Object[] args, Method method, String key) {
        // 文件名不包含"#",直接返回
        if (!key.contains("#")) {
            return key;
        }
        EvaluationContext context = new MethodBasedEvaluationContext(null, method, args, NAME_DISCOVERER);
        final Object value = PARSER.parseExpression(key).getValue(context);
        return value == null ? null : value.toString();
    }
}
