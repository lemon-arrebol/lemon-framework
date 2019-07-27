package com.lemon.common.util;

import com.google.common.base.Preconditions;
import com.lemon.common.exception.DigestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * @author lemon
 * @version 1.0
 * @description: 摘要工具
 * @date Create by lemon on 2019-07-26 16:35
 */
@Slf4j
public class DigestUtil {
    private static final String DEFAULT_DIGEST_ALGORITHM = "MD5";
    public static final int DEFAULT_ITERATIONS_SIZE = 1;

    /**
     * @param
     * @return
     * @description 使用默认算MD5加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 17:32
     */
    public static String digest(String data) {
        return DigestUtil.digest(DigestUtil.DEFAULT_DIGEST_ALGORITHM, CodecUtil.decodeUtf8(data), SaltUtil.generateSalt(), DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 17:32
     */
    public static String digest(String algorithm, String data) {
        return DigestUtil.digest(algorithm, CodecUtil.decodeUtf8(data), SaltUtil.generateSalt(), DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 17:32
     */
    public static String digest(String algorithm, String data, byte[] salt) {
        return DigestUtil.digest(algorithm, CodecUtil.decodeUtf8(data), salt, DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 17:32
     */
    public static String digest(String algorithm, String data, byte[] salt, int iterations) {
        return DigestUtil.digest(algorithm, CodecUtil.decodeUtf8(data), salt, iterations);
    }

    /**
     * @param
     * @return
     * @description 使用默认算MD5加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 16:51
     */
    public static String digest(byte[] data) {
        return DigestUtil.digest(DigestUtil.DEFAULT_DIGEST_ALGORITHM, data, SaltUtil.generateSalt(), DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 16:51
     */
    public static String digest(String algorithm, byte[] data) {
        return DigestUtil.digest(algorithm, data, SaltUtil.generateSalt(), DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 16:51
     */
    public static String digest(String algorithm, byte[] data, byte[] salt) {
        return DigestUtil.digest(algorithm, data, salt, DigestUtil.DEFAULT_ITERATIONS_SIZE);
    }

    /**
     * @param
     * @return
     * @description 按照指定算法加盐生成摘要: 16进制(摘要) + 16进制(盐)
     * @author lemon
     * @date 2019-07-26 16:51
     */
    public static String digest(String algorithm, byte[] data, byte[] salt, int iterations) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(data), "Data is not allowed to be empty");

        if (StringUtils.isBlank(algorithm)) {
            algorithm = DigestUtil.DEFAULT_DIGEST_ALGORITHM;
            log.warn("Unspecified algorithm uses default algorithm [%s]", algorithm);
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            if (ArrayUtils.isNotEmpty(salt)) {
                messageDigest.update(salt);
            }

            byte[] result = messageDigest.digest(data);

            if (iterations > 1) {
                for (int i = 1; i < iterations; ++i) {
                    messageDigest.reset();
                    result = messageDigest.digest(result);
                }
            }

            String saltStr = CodecUtil.encodeHex(salt);
            String resultStr = CodecUtil.encodeHex(result);

            if (log.isDebugEnabled()) {
                log.debug("Hex digest[{}], hex salt[{}]", resultStr, saltStr);
            }

            return String.format("%s%s", resultStr, saltStr);
        } catch (GeneralSecurityException e) {
            throw new DigestException("摘要生成失败", e);
        }
    }

    /**
     * @param
     * @return
     * @description 验证摘要
     * @author lemon
     * @date 2019-07-26 17:48
     */
    public static boolean verify(String algorithm, String digest, String data, int saltSize, int iterations) {
        Preconditions.checkArgument(saltSize >= 0, "Salt size must be greater than or equal to 0");

        String salt = saltSize == 0 ? null : digest.substring(digest.length() - saltSize * 2);
        String newDigest = DigestUtil.digest(algorithm, data, CodecUtil.decodeHex(salt));
        return digest.equals(newDigest);
    }
}
