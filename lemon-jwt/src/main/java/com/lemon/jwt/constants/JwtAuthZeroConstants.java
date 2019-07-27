package com.lemon.jwt.constants;

/**
 * @author lemon
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
 * @date Create by lemon on 2019-07-21 22:51
 */
public class JwtAuthZeroConstants {
    /**
     * HMAC using SHA-256
     */
    public static final String HMAC256 = "HMAC256";

    /**
     * HMAC using SHA-384
     */
    public static final String HMAC384 = "HMAC384";

    /**
     * HMAC using SHA-512
     */
    public static final String HMAC512 = "HMAC512";

    /**
     * RSASSA-PKCS-v1_5 using SHA-256
     */
    public static final String RSA256 = "RSA256";

    /**
     * RSASSA-PKCS-v1_5 using SHA-384
     */
    public static final String RSA384 = "RSA384";

    /**
     * RSASSA-PKCS-v1_5 using SHA-512
     */
    public static final String RSA512 = "RSA512";

    /**
     * ECDSA using P-256 and SHA-256
     */
    public static final String ECDSA256 = "ECDSA256";

    /**
     * ECDSA using P-384 and SHA-384
     */
    public static final String ECDSA384 = "ECDSA384";

    /**
     * ECDSA using P-512 and SHA-512
     */
    public static final String ECDSA512 = "ECDSA512";
}
