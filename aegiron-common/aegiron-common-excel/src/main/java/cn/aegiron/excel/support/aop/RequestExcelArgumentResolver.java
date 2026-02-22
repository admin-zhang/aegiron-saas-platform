package cn.aegiron.excel.support.aop;

import cn.aegiron.excel.annotation.RequestExcel;
import cn.aegiron.excel.support.converts.LocalDateStringConverter;
import cn.aegiron.excel.support.converts.LocalDateTimeStringConverter;
import cn.aegiron.excel.support.handler.input.ListAnalysisEventListener;
import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.InputStream;
import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName RequestExcelArgumentResolver.java
 * @Description 请求接口的参数被 @RequestExcel 修饰的参数解析器(Excel上传)
 * @since 2026/2/8 17:58
 */
@Slf4j
public class RequestExcelArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 只处理被 @RequestExcel 修饰的方法
     *
     * @param parameter 封装方法参数的对象
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断当前方法的参数是否被 @RequestExcel 修饰
        return parameter.hasParameterAnnotation(RequestExcel.class);
    }

    /**
     * 解析参数的值
     *
     * @param parameter             封装方法参数的对象
     * @param modelAndViewContainer 当前请求的 ModelAndViewContainer
     * @param webRequest            当前请求
     * @param webDataBinderFactory  用于创建 WebDataBinder 实例的工厂
     */
    @Override
    @SneakyThrows(Exception.class)
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) {
        Class<?> parameterType = parameter.getParameterType();
        // Excel 导入必须是 List 类型
        if (!parameterType.isAssignableFrom(List.class)) {
            throw new IllegalArgumentException("Excel upload request resolver error, @RequestExcel parameter is not List " + parameterType);
        }
        // 处理自定义 readListener
        RequestExcel requestExcel = parameter.getParameterAnnotation(RequestExcel.class);
        assert requestExcel != null;
        Class<? extends ListAnalysisEventListener<?>> readListenerClass = requestExcel.readListener();
        ListAnalysisEventListener<?> readListener = BeanUtils.instantiateClass(readListenerClass);

        // 获取请求文件流
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        InputStream inputStream;
        if (request instanceof MultipartRequest) {
            MultipartFile file = ((MultipartRequest) request).getFile(requestExcel.fileName());
            assert file != null;
            inputStream = file.getInputStream();
        } else {
            inputStream = request.getInputStream();
        }

        // 获取目标类型
        Class<?> excelModelClass = ResolvableType.forMethodParameter(parameter).getGeneric(0).resolve();

        // 这里需要指定读用哪个 class 去读，然后读取第一个 sheet 文件流会自动关闭
        EasyExcel.read(inputStream, excelModelClass, readListener)
                .registerConverter(LocalDateStringConverter.INSTANCE)
                .registerConverter(LocalDateTimeStringConverter.INSTANCE)
                .ignoreEmptyRow(requestExcel.ignoreEmptyRow())
                .sheet().doRead();

        // 校验失败的数据处理 交给 BindResult
        WebDataBinder dataBinder = webDataBinderFactory.createBinder(webRequest, readListener.getErrors(), "excel");
        ModelMap model = modelAndViewContainer.getModel();
        model.put(BindingResult.MODEL_KEY_PREFIX + "excel", dataBinder.getBindingResult());

        return readListener.getList();
    }
}
