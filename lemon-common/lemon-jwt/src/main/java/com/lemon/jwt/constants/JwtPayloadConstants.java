package com.lemon.jwt.constants;

/**
 * @author houjuntao
 * @version 1.0
 * @description: JWT Payload 部分也是一个 JSON 对象，用来存放实际需要传递的数据。JWT 规定了7个官方字段，供选用。
 * 注意，JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。
 * iss (issuer)：签发人
 * exp (expiration time)：过期时间
 * sub (subject)：主题
 * aud (audience)：受众
 * nbf (Not Before)：生效时间
 * iat (Issued At)：签发时间
 * jti (JWT ID)：编号
 * @date Create by houjuntao on 2019-07-21 22:51
 */
public class JwtPayloadConstants {
    /**
     * iss (issuer)：签发人
     */
    public static final String ISSUER = "iss";

    /**
     * sub (subject)：主题
     */
    public static final String SUBJECT = "sub";

    /**
     * aud (audience)：受众
     */
    public static final String AUDIENCE = "aud";

    /**
     * exp (expiration time)：过期时间
     */
    public static final String EXPIRATION = "exp";

    /**
     * nbf (Not Before)：生效时间
     */
    public static final String NOT_BEFORE = "nbf";

    /**
     * iat (Issued At)：签发时间
     */
    public static final String ISSUED_AT = "iat";

    /**
     * jti (JWT ID)：编号
     */
    public static final String ID = "jti";
}
