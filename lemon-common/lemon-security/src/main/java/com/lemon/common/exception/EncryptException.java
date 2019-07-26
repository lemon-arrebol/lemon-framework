package com.lemon.common.exception;

/**
 * @author lemon
 * @version 1.0
 * @description: 加密异常
 * @date Create by lemon on 2019-07-24 18:40
 */
public class EncryptException extends RuntimeException {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public EncryptException() {
        super();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public EncryptException(String message) {
        super(message);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public EncryptException(Throwable cause) {
        super(cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    protected EncryptException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
