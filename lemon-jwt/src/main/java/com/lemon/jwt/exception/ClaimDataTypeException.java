package com.lemon.jwt.exception;

public class ClaimDataTypeException extends LemonJwtException {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public ClaimDataTypeException() {
        super();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public ClaimDataTypeException(String message) {
        super(message);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public ClaimDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    public ClaimDataTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-24 18:41
     */
    protected ClaimDataTypeException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
