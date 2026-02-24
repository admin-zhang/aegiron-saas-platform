package cn.aegiron.api;

/**
 * @author panda
 * @version 1.0
 * @ClassName ResultCode.java
 * @Description 错误码
 * @since 2026/2/8 16:59
 */
public enum ResultCode implements IErrorCode{

    SUCCESS("200", "操作成功"),
    FAILED("500", "操作失败"),
    VALIDATE_FAILED("404", "参数检验失败"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    FORBIDDEN("403", "没有相关权限");

    private final String code;
    private final String message;

    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
