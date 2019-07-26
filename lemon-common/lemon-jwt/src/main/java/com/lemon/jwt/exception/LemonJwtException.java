package com.lemon.jwt.exception;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-07-24 18:40
 */
public class LemonJwtException extends RuntimeException {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public LemonJwtException() {
        super();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public LemonJwtException(String message) {
        super(message);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public LemonJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public LemonJwtException(Throwable cause) {
        super(cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    protected LemonJwtException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
