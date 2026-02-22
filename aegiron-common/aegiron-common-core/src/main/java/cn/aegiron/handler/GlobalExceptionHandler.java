package cn.aegiron.handler;

import cn.aegiron.api.IErrorCode;
import cn.aegiron.api.CommonResult;
import cn.aegiron.exception.AegironException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName GlobalExceptionHandler.java
 * @Description 全局异常处理
 * @since 2026/2/8 17:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(AegironException.class)
    public CommonResult<Object> iCourseExceptionHandle(AegironException e) {
        // 记录异常的完整堆栈跟踪信息
        logException(e);
        // 处理异常并返回结果
        return handleException(e);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Object> validationExceptionHandle(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        // 记录异常的完整堆栈跟踪信息
        logException(e);
        return CommonResult.failed(allErrors.get(0).getDefaultMessage());
    }

    private void logException(Exception e) {
        // 记录异常的完整堆栈跟踪信息
        logger.error("ICourseException occurred: {}", e.getMessage(), e);
    }

    private CommonResult<Object> handleException(AegironException e) {
        IErrorCode errorCode = e.getErrorCode();
        if (errorCode != null) {
            return CommonResult.failed(errorCode, e.getObject());
        }

        String errorMessage = e.getMessage();
        if (errorMessage == null) {
            errorMessage = "An unknown error occurred";
        }
        return CommonResult.failed(errorMessage);
    }
}
