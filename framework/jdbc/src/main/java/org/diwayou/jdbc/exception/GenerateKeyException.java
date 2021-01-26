package org.diwayou.jdbc.exception;

/**
 * @author gaopeng 2021/1/20
 */
public class GenerateKeyException extends RuntimeException {
    public GenerateKeyException() {
    }

    public GenerateKeyException(String message) {
        super(message);
    }

    public GenerateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateKeyException(Throwable cause) {
        super(cause);
    }

    public GenerateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
