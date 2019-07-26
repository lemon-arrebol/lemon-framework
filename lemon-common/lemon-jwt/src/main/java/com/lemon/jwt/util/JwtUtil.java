package com.lemon.jwt.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lemon.jwt.constants.JwtConstants;
import com.lemon.common.util.CodecUtil;
import com.lemon.common.util.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Map;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-07-20 21:15
 */
@Slf4j
public class JwtUtil {
    /**
     * @param token      加密后的token
     * @param signingKey 签名的秘钥
     * @return
     * @description 解密JWT
     * @author lemon
     * @date 2019-07-20 21:23
     */
    public static Claims parseJWT(String token, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(signingKey)
                // 设置需要解析的jwt
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Header http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html
     * @author lemon
     * @date 2019-07-20 21:38
     */
    public static JSONObject parseJWTHeader(String token) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");

        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(CodecUtil.decodeBase64(token.split("[.]")[0])));
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Payload
     * @author lemon
     * @date 2019-07-20 21:42
     */
    public static JSONObject parseJWTPayload(String token) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");

        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(CodecUtil.decodeBase64(token.split("[.]")[1])));
    }

    /**
     * @param
     * @return
     * @description 解析JWT的Signature
     * @author lemon
     * @date 2019-07-20 21:38
     */
    public static JSONObject parseJWTSignatured(String token) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");

        return StringUtils.isBlank(token) ? new JSONObject() : JSON.parseObject(new String(CodecUtil.decodeBase64(token.split("[.]")[2])));
    }

    /**
     * @param
     * @return
     * @description JWT token是否过期
     * @author lemon
     * @date 2019-07-20 23:12
     */
    public static boolean isExpiration(String token) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");

        JSONObject jsonObject = JwtUtil.parseJWTPayload(token);
        Long expiration = jsonObject.getLong(JwtConstants.EXPIRATION);

        if (expiration == null) {
            if (log.isDebugEnabled()) {
                log.debug("Jwt token{}, expiration time is null", token);
            }

            return false;
        }

        return TimeUtil.isBeforeOfCurrentByMilli(expiration);
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，只允许使用HS256、HS384、HS512
     * @author lemon
     * @date 2019-07-20 22:49
     */
    public static String createJWTUseHmac(Map<String, Object> claimMap, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        return JwtUtil.createJWTUseHmac(claimMap, (String) null, signingKey);
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，只允许使用HS256、HS384、HS512
     * @author lemon
     * @date 2019-07-20 22:49
     */
    public static String createJWTUseHmac(Map<String, Object> claimMap, String algorithm, String signingKey) {
        Preconditions.checkArgument(StringUtils.isBlank(algorithm) || "HS256".equals(algorithm) || "HS384".equals(algorithm) || "HS512".equals(algorithm), "The algorithm allows the value to be HS256、HS384、HS512");
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        SignatureAlgorithm signatureAlgorithm = JwtUtil.getSignatureAlgorithm(algorithm);
        return JwtUtil.createJWTUseHmac(claimMap, signatureAlgorithm, signingKey);
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，只允许使用HS256、HS384、HS512
     * @author lemon
     * @date 2019-07-20 22:29
     */
    public static String createJWTUseHmac(Map<String, Object> claimMap, SignatureAlgorithm signatureAlgorithm, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");
        Preconditions.checkArgument(MapUtils.isNotEmpty(claimMap), "Claims not allowed to be empty");

        if (claimMap == null) {
            claimMap = Maps.newHashMap();
            claimMap.put(JwtConstants.ISSUED_AT, System.currentTimeMillis());
        }

        JwtBuilder builder = Jwts.builder()
                // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
                .addClaims(claimMap)
//                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
//                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
//                .setId(jwtId)
//                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
//                .setSubject(subject)
//                // iat: jwt的签发时间
//                .setIssuedAt(Calendar.getInstance().getTime())
//                // 设置过期时间
//                builder.setExpiration(exp)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，jwtId默认使用UUID
     * @author lemon
     * @date 2019-07-20 22:49
     */
    public static String createJWT(Map<String, Object> claimMap, Key signingKey) {
        Preconditions.checkNotNull(signingKey, "SigningKey not allowed to be empty");

        return JwtUtil.createJWT(claimMap, (String) null, signingKey);
    }

    /**
     * @param
     * @return
     * @description 默认使用SignatureAlgorithm.HS256算法签名JWT，jwtId默认使用UUID
     * @author lemon
     * @date 2019-07-20 22:49
     */
    public static String createJWT(Map<String, Object> claimMap, String algorithm, Key signingKey) {
        Preconditions.checkArgument(StringUtils.isBlank(algorithm) || "HS256".equals(algorithm) || "HS384".equals(algorithm) || "HS512".equals(algorithm), "The algorithm allows the value to be HS256、HS384、HS512");
        Preconditions.checkNotNull(signingKey, "SigningKey not allowed to be empty");

        SignatureAlgorithm signatureAlgorithm = JwtUtil.getSignatureAlgorithm(algorithm);
        return JwtUtil.createJWT(claimMap, signatureAlgorithm, signingKey);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-20 22:29
     */
    public static String createJWT(Map<String, Object> claimMap, SignatureAlgorithm signatureAlgorithm, Key signingKey) {
        Preconditions.checkNotNull(signingKey, "SigningKey not allowed to be empty");
        Preconditions.checkArgument(MapUtils.isNotEmpty(claimMap), "Claims not allowed to be empty");

        if (claimMap == null) {
            claimMap = Maps.newHashMap();
            claimMap.put(JwtConstants.ISSUED_AT, System.currentTimeMillis());
        }

        JwtBuilder builder = Jwts.builder()
                // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
                .addClaims(claimMap)
//                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
//                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
//                .setId(jwtId)
//                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
//                .setSubject(subject)
//                // iat: jwt的签发时间
//                .setIssuedAt(Calendar.getInstance().getTime())
//                // 设置过期时间
//                builder.setExpiration(exp)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    /**
     * @param
     * @return
     * @description 校验token
     * @author lemon
     * @date 2019-07-20 22:25
     */
    public static Boolean isVerify(String token, String signingKey) {
        try {
            // 得到DefaultJwtParser
            Jwts.parser()
                    // 设置签名的秘钥，签名秘钥，和生成的签名的秘钥一模一样
                    .setSigningKey(signingKey)
                    // 设置需要解析的jwt
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error(String.format("token[%s]解析失败", token), e);
            return false;
        }

        return true;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-25 17:42
     */
    public static SignatureAlgorithm getSignatureAlgorithm(String algorithm) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm;

        if (StringUtils.isBlank(algorithm)) {
            signatureAlgorithm = SignatureAlgorithm.HS256;
        } else {
            signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        }

        return signatureAlgorithm;
    }
}
