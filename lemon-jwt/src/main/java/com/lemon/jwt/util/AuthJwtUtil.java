package com.lemon.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lemon.jwt.constants.JwtAuthZeroConstants;
import com.lemon.jwt.constants.JwtConstants;
import com.lemon.jwt.exception.ClaimDataTypeException;
import com.lemon.common.util.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-07-23 18:58
 */
@Slf4j
public class AuthJwtUtil {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-23 19:01
     */
    public static String createJWTUseHmac(Map<String, Object> headerClaimMap, Map<String, Object> claimMap, String algorithm, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        return AuthJwtUtil.createJWTUseHmac(headerClaimMap, claimMap, algorithm, CodecUtil.decodeUtf8(signingKey));
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-23 19:01
     */
    public static String createJWTUseHmac(Map<String, Object> headerClaimMap, Map<String, Object> claimMap, String algorithm, byte[] signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(signingKey), "SigningKey not allowed to be empty");

        Algorithm jwtAlgorithm = AuthJwtUtil.getHMACAlgorithm(algorithm, signingKey);
        return AuthJwtUtil.createJWT(headerClaimMap, claimMap, jwtAlgorithm);
    }

    /**
     * @param
     * @return
     * @description API不够有好，不停的用withClaim放数据。
     * @author lemon
     * @date 2019-07-25 11:17
     */
    public static String createJWT(Map<String, Object> headerClaimMap, Map<String, Object> claimMap, Algorithm jwtAlgorithm) {
        Preconditions.checkArgument(jwtAlgorithm != null, "Must specify an algorithm");

        if (headerClaimMap == null) {
            headerClaimMap = Maps.newHashMap();
        }

        headerClaimMap.put(JwtConstants.HEADER_ALG, jwtAlgorithm.getName());
        headerClaimMap.put(JwtConstants.HEADER_TYP, JwtConstants.HEADER_TYP_JWT);

        JWTCreator.Builder builder = JWT.create()
                .withHeader(headerClaimMap);
//                    // 代表这个JWT的签发者
//                    .withIssuer("")
//                    // key
//                    .withKeyId("")
//                    // 是JWT的唯一标识。
//                    .withJWTId("")
//                    // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
//                    .withNotBefore(null)
//                    // 代表这个JWT的主体，即它的所有人；
//                    .withSubject(null)
//                    // 代表这个JWT的接收对象；
//                    .withAudience()
//                    // 是一个时间戳，代表这个JWT的签发时间；
//                    .withIssuedAt(null)
//                    // 过期时间
//                    .withExpiresAt(null)

        if (claimMap == null) {
            claimMap = Maps.newHashMap();
            claimMap.put(JwtConstants.ISSUED_AT, System.currentTimeMillis());
        }

        claimMap.forEach((key, value) -> {
            if (value == null) {
                builder.withClaim(key, (String) null);
                log.warn("key[{}]未指定值Auth0将会移除", key);
                return;
            }

            Class valueClass = value.getClass();

            if (value.getClass().isArray()) {
                Class componentType = valueClass.getComponentType();

                if (componentType == int.class) {
                    builder.withArrayClaim(key, Arrays.stream((int[]) value).boxed().toArray(Integer[]::new));
                } else if (componentType == Integer.class) {
                    builder.withArrayClaim(key, (Integer[]) value);
                } else if (componentType == long.class) {
                    builder.withArrayClaim(key, Arrays.stream((long[]) value).boxed().toArray(Long[]::new));
                } else if (componentType == Long.class) {
                    builder.withArrayClaim(key, (Long[]) value);
                } else if (componentType == String.class) {
                    builder.withArrayClaim(key, (String[]) value);
                } else {
                    throw new ClaimDataTypeException(String.format("Auth0 does not support array element type '%s'", componentType));
                }
            } else if (valueClass == Boolean.class || valueClass == boolean.class) {
                builder.withClaim(key, (Boolean) value);
            } else if (valueClass == Integer.class || valueClass == int.class) {
                builder.withClaim(key, (Integer) value);
            } else if (valueClass == Long.class || valueClass == long.class) {
                builder.withClaim(key, (Long) value);
            } else if (valueClass == Double.class || valueClass == double.class) {
                builder.withClaim(key, (Double) value);
            } else if (valueClass == String.class) {
                builder.withClaim(key, (String) value);
            } else if (valueClass == Date.class) {
                builder.withClaim(key, (Date) value);
            } else {
                throw new ClaimDataTypeException(String.format("Auth0 does not support element type '%s'", valueClass));
            }
        });

        String token = builder.sign(jwtAlgorithm);
        return token;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-25 14:10
     */
    public static Map<String, Claim> parseJWTUseHmac(String token, String algorithm, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        return AuthJwtUtil.parseJWTUseHmac(token, algorithm, CodecUtil.decodeUtf8(signingKey));
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-25 14:10
     */
    public static Map<String, Claim> parseJWTUseHmac(String token, String algorithm, byte[] signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(signingKey), "SigningKey not allowed to be empty");

        Algorithm jwtAlgorithm = AuthJwtUtil.getHMACAlgorithm(algorithm, signingKey);
        return AuthJwtUtil.parseJWT(token, jwtAlgorithm);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-23 19:01
     */
    public static Map<String, Claim> parseJWT(String token, Algorithm jwtAlgorithm) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "Token not allowed to be empty");
        Preconditions.checkArgument(jwtAlgorithm != null, "Must specify an algorithm");

        try {
            // 创建解析器，用来将String的令牌解析成jwt对象
            JWTVerifier verifier = JWT.require(jwtAlgorithm)
                    .build();
            // 解析token
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-25 14:07
     */
    public static Algorithm getHmacAlgorithm(String algorithm, String signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(signingKey), "SigningKey not allowed to be empty");

        return AuthJwtUtil.getHMACAlgorithm(algorithm, CodecUtil.decodeUtf8(signingKey));
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-07-25 14:08
     */
    public static Algorithm getHMACAlgorithm(String algorithm, byte[] signingKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(signingKey), "SigningKey not allowed to be empty");

        Algorithm jwtAlgorithm = null;

        switch (algorithm) {
            case JwtAuthZeroConstants.HMAC256:
                jwtAlgorithm = Algorithm.HMAC256(signingKey);
                break;

            case JwtAuthZeroConstants.HMAC384:
                jwtAlgorithm = Algorithm.HMAC384(signingKey);
                break;

            case JwtAuthZeroConstants.HMAC512:
                jwtAlgorithm = Algorithm.HMAC512(signingKey);
                break;
        }

        if (jwtAlgorithm == null) {
            jwtAlgorithm = Algorithm.HMAC256(signingKey);
            log.warn("HMAC '{}' algorithm is invalid, Only supported [HMAC256、 HMAC384、HMAC512], use defaule algorithm 'HMAC256'", algorithm);
        }

        return jwtAlgorithm;
    }
}