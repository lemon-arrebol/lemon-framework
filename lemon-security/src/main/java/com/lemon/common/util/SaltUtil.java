package com.lemon.common.util;

import com.google.common.base.Preconditions;

import java.security.SecureRandom;

/**
 * @author lemon
 * @version 1.0
 * @description: 生成盐工具
 * @date Create by lemon on 2019-07-26 16:38
 */
public class SaltUtil {
    public static final int DEFAULT_SALT_SIZE = 16;
    private static final byte[] EMPTY_SALTE = {};
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * @param
     * @return
     * @description 默认生成指定16字节的盐
     * @author lemon
     * @date 2019-07-26 16:41
     */
    public static byte[] generateSalt() {
        return SaltUtil.generateSalt(SaltUtil.DEFAULT_SALT_SIZE);
    }

    /**
     * @param
     * @return
     * @description 生成指定数量字节的盐
     * @author lemon
     * @date 2019-07-26 16:40
     */
    public static byte[] generateSalt(int saltSize) {
        return SaltUtil.generateSalt(saltSize, SaltUtil.SECURE_RANDOM);
    }

    /**
     * @param
     * @return
     * @description 生成指定数量字节的盐
     * @author lemon
     * @date 2019-07-26 16:42
     */
    public static byte[] generateSalt(int saltSize, SecureRandom secureRandom) {
        Preconditions.checkArgument(saltSize >= 0, "Salt size must be greater than or equal to 1");

        if (saltSize == 0) {
            return SaltUtil.EMPTY_SALTE;
        }

        byte[] bytes = new byte[saltSize];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
