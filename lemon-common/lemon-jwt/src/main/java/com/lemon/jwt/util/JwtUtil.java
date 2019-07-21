package com.lemon.jwt.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lemon.jwt.constants.JwtPayloadConstants;
import com.lemon.util.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author houjuntao
 * @version 1.0
 * @description: TODO
 * @date Create by houjuntao on 2019-07-20 21:15
 */
@Slf4j
public class JwtUtil {
    /**
     * @param token      加密后的token
     * @param signingKey 签名的秘钥
     * @return
     * @description 解密JWT
     * @author houjuntao
     * @date 2019-07-20 21:23
     */
    public static Claims parseJWT(String token, String signingKey) {
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(signingKey.getBytes())
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Header http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html
     * @author houjuntao
     * @date 2019-07-20 21:38
     */
    public static JSONObject parseJWTHeader(String token) {
        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(Base64.decodeBase64(token.split("[.]")[0])));
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Payload
     * @author houjuntao
     * @date 2019-07-20 21:42
     */
    public static JSONObject parseJWTPayload(String token) {
        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(Base64.decodeBase64(token.split("[.]")[1])));
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Signature
     * @author houjuntao
     * @date 2019-07-20 21:38
     */
    public static JSONObject parseJWTSignatured(String token) {
        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(Base64.decodeBase64(token.split("[.]")[2])));
    }

    /**
     * @param
     * @return
     * @description JWT token是否过期
     * @author houjuntao
     * @date 2019-07-20 23:12
     */
    public static boolean isExpiration(String token) {
        JSONObject jsonObject = JwtUtil.parseJWTPayload(token);
        Long expiration = jsonObject.getLong(JwtPayloadConstants.EXPIRATION);

        if (expiration == null) {
            if (log.isDebugEnabled()) {
                log.debug("Jwt token{}, expiration time is null", token);
            }

            return true;
        }

        return TimeUtil.isBeforeOfCurrentByMilli(expiration);
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，jwtId默认使用UUID
     * @author houjuntao
     * @date 2019-07-20 22:49
     */
    public static String createJWT(Map<String, Object> claimMap, String signingKey) {
        try {
            return JwtUtil.createJWT(claimMap, null, signingKey, UUID.randomUUID().toString(), null, -1L);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param
     * @return
     * @description
     * @author houjuntao
     * @date 2019-07-20 22:49
     */
    public static String createJWT(Map<String, Object> claimMap, String algorithm, String jwtId, String signingKey) {
        try {
            return JwtUtil.createJWT(claimMap, algorithm, signingKey, jwtId, null, -1L);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     * @param
     * @return
     * @description
     * @author houjuntao
     * @date 2019-07-20 22:29
     */
    public static String createJWT(Map<String, Object> claimMap, String algorithm, String jwtId, String signingKey, String subject, long ttlMillis) throws UnsupportedEncodingException {
        Assert.notNull(jwtId, "JWT ID not allowed to be empty");
        Assert.notNull(signingKey, "SigningKey not allowed to be empty");
        Assert.notEmpty(claimMap, "Claims not allowed to be empty");

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm;

        if (StringUtils.isBlank(algorithm)) {
            signatureAlgorithm = SignatureAlgorithm.HS256;
        } else {
            signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        }


        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        byte[] apiKeySecretBytes = signingKey.getBytes("UTF-8");
        Key signingKeySec = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
                .addClaims(claimMap)
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(jwtId)
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, signingKeySec);

        // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
        if (StringUtils.isNotBlank(subject)) {
            builder.setSubject(subject);
        }

        if (ttlMillis >= 0L) {
            long expMillis = System.currentTimeMillis() + ttlMillis;
            Date exp = new Date(expMillis);
            // 设置过期时间
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    /**
     * @param
     * @return
     * @description 校验token
     * @author houjuntao
     * @date 2019-07-20 22:25
     */
    public static Boolean isVerify(String token, String signingKey) {
        return JwtUtil.isVerify(token, signingKey, null);
    }

    /**
     * @param
     * @return
     * @description 校验token
     * @author houjuntao
     * @date 2019-07-20 22:25
     */
    public static Boolean isVerify(String token, String signingKey, String subject) {
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥，签名秘钥，和生成的签名的秘钥一模一样
                .setSigningKey(signingKey)
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (StringUtils.isBlank(subject) || subject.equals(claims.getSubject())) {
            return true;
        }

        return false;
    }
}
