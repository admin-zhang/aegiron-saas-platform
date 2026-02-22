package cn.aegiron.api;

import lombok.Data;

/**
 * @author panda
 * @version 1.0
 * @ClassName CommonResult.java
 * @Description 统一返回结果
 * @since 2026/2/8 16:58
 */
@Data
public class CommonResult<T> {

    private String code;

    private String message;

    private T data;

    protected CommonResult() {
    }

    /**
     * 用于返回结果处理
     *
     * @param code 状态码
     * @param message 提示信息
     */
    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 用于返回结果处理
     *
     * @param code 状态码
     * @param message 提示信息
     * @param data 返回数据
     */
    private CommonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 封装成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    /**
     * 封装成功返回结果
     *
     * @param message 提示信息
     * @param data 返回数据
     */
    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 封装成功返回结果
     *
     * @param data 返回数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 封装失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 封装失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 封装失败返回结果
     *
     * @param message 错误信息
     */
    public static CommonResult<Object> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message);
    }

    /**
     * 封装失败返回结果
     *
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<>(errorCode.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode, T data) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

}
