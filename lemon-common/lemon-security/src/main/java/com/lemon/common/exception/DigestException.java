package com.lemon.common.exception;

/**
 * @param
 * @author lemon
 * @description 签名异常
 * @return
 * @date 2019-07-26 15:28
 */
public class DigestException extends RuntimeException {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public DigestException() {
        super();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public DigestException(String message) {
        super(message);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public DigestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public DigestException(Throwable cause) {
        super(cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    protected DigestException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
