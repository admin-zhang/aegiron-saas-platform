package cn.aegiron.excel.support.aop;

import cn.aegiron.excel.annotation.ResponseExcel;
import cn.aegiron.excel.support.handler.output.SheetWriteHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName ResponseExcelReturnValueHandler.java
 * @Description 注解 @ResponseExcel 所标注的接口的返回值处理器(Excel导出)
 * @since 2026/2/8 17:59
 */
@Slf4j
@RequiredArgsConstructor
public class ResponseExcelReturnValueHandler implements HandlerMethodReturnValueHandler {


    /**
     * Excel 工作表导出处理器列表
     */
    private final List<SheetWriteHandler> sheetWriteHandlerList;

    /**
     * 只处理 @ResponseExcel 声明的方法
     *
     * @param parameter 封装方法参数的对象
     */
    @Override
    public boolean supportsReturnType(MethodParameter parameter) {
        return parameter.getMethodAnnotation(ResponseExcel.class) != null;
    }

    /**
     * 处理 @ResponseExcel 修饰的接口的返回值
     *
     * @param date             接口返回值,两种类型List<?>或者List<List<?>>
     * @param parameter        封装方法参数的对象
     * @param mavContainer     上下文容器
     * @param nativeWebRequest 上下文
     */
    @Override
    public void handleReturnValue(Object date, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest) {
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        ResponseExcel responseExcel = parameter.getMethodAnnotation(ResponseExcel.class);
        Assert.state(responseExcel != null, "Method does not exist @ResponseExcel");
        mavContainer.setRequestHandled(true);
        // 找到合适的 Excel 导出处理器
        sheetWriteHandlerList.stream()
                .filter(handler -> handler.support(date))
                .findFirst()
                .ifPresent(handler -> handler.export(date, response, responseExcel));
    }
}
