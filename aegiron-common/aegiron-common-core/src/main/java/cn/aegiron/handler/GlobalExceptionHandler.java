package cn.aegiron.handler;

import cn.aegiron.api.CommonResult;
import cn.aegiron.api.IErrorCode;
import cn.aegiron.api.ResultCode;
import cn.aegiron.exception.AegironException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理器
 * 参考 jlyx-common GlobalRestExceptionHandler 完善
 *
 * @author panda
 * @since 2026/2/8
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(AegironException.class)
    public CommonResult<Object> handleAegironException(AegironException e) {
        logException("AegironException", e);
        return handleException(e);
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CommonResult<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        logException("Method Not Allowed", e);
        return CommonResult.of(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), e.getMessage(), null);
    }

    /**
     * 参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> handleMissingParameter(MissingServletRequestParameterException e) {
        logException("Missing Servlet Request Parameter", e);
        return CommonResult.of(ResultCode.VALIDATE_FAILED.getCode(),
                "请求参数缺失: " + e.getParameterName(), null);
    }

    /**
     * 请求参数类型错误异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        logException("Method Argument Type Mismatch", e);
        return CommonResult.of(ResultCode.VALIDATE_FAILED.getCode(),
                "请求参数类型错误: " + e.getMessage(), null);
    }

    /**
     * JSR303 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> handleValidationException(MethodArgumentNotValidException e) {
        logException("Validation Exception", e);
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElseGet(() -> e.getBindingResult().getAllErrors().stream()
                        .map(err -> err.getDefaultMessage())
                        .findFirst()
                        .orElse("参数校验错误"));
        return CommonResult.of(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 缺少请求体异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> handleMessageNotReadable(HttpMessageNotReadableException e) {
        logException("Http Message Not Readable", e);
        return CommonResult.of(ResultCode.VALIDATE_FAILED.getCode(), "参数体不能为空", null);
    }

    /**
     * 访问拒绝异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult<Object> handleAccessDenied(HttpServletRequest req, AccessDeniedException e) {
        logException("Access Denied", e);
        return CommonResult.forbidden(null);
    }

    /**
     * 兜底异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<Object> handleDefaultException(HttpServletRequest request, Throwable e) {
        logException("Internal Server Error", e);
        return CommonResult.failed(ResultCode.FAILED.getMessage());
    }

    private void logException(String type, Throwable e) {
        log.error("{}: {}", type, e.getMessage(), e);
    }

    private CommonResult<Object> handleException(AegironException e) {
        IErrorCode errorCode = e.getErrorCode();
        if (errorCode != null) {
            return CommonResult.failed(errorCode, e.getObject());
        }
        String errorMessage = e.getMessage();
        if (errorMessage == null) {
            errorMessage = "未知错误";
        }
        return CommonResult.failed(errorMessage);
    }
}
