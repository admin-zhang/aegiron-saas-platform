package cn.aegiron.exception;

import cn.aegiron.api.IErrorCode;
import lombok.Getter;

import java.io.Serial;

/**
 * @author panda
 * @version 1.0
 * @ClassName AegironException.java
 * @Description 自定义异常类
 * @since 2026/2/8 17:27
 */
@Getter
public class AegironException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = -9142335160830510781L;

    private Object object;

    private IErrorCode errorCode;

    public AegironException(String message) {
        super(message);
    }

    public AegironException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public AegironException(String message, Throwable cause) {
        super(message, cause);
    }

    public AegironException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AegironException(IErrorCode errorCode,Object object) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.object = object;
    }
}
