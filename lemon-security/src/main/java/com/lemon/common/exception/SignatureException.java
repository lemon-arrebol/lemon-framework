package com.lemon.common.exception;

/**
 * @param
 * @author lemon
 * @description 签名异常
 * @return
 * @date 2019-07-26 15:28
 */
public class SignatureException extends RuntimeException {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public SignatureException() {
        super();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public SignatureException(String message) {
        super(message);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public SignatureException(Throwable cause) {
        super(cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    protected SignatureException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
