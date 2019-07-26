package com.lemon.common.algorithm;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author lemon
 * @version 1.0
 * @description: RSA密钥对生成器
 * @date Create by lemon on 2019-07-26 14:32
 */
@Slf4j
public class RSAKeyPairGenerator {
    /**
     * @param
     * @return
     * @description 构建RSA密钥对
     * @author lemon
     * @date 2019-07-25 18:24
     */
    public static RSAKeyPair generateKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");

        if (log.isDebugEnabled()) {
            log.debug("algorithm[{}], keySize[{}]", algorithm, keySize);
        }

        // 实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 初始化密钥生成器
        keyPairGenerator.initialize(keySize);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAKeyPair rsaKeyPair = new RSAKeyPair((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());

        if (log.isDebugEnabled()) {
            log.debug("publicKey format[{}], privateKey format[{}]", keyPair.getPublic().getFormat(), keyPair.getPrivate().getFormat());
        }

        return rsaKeyPair;
    }
}
