package com.lemon.common.algorithm;

import com.lemon.common.util.CodecUtil;
import lombok.Getter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @param
 * @author lemon
 * @description RSA密钥对
 * @return
 * @date 2019-07-25 18:24
 */
@Getter
public class RSAKeyPair {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private String publicKeyText;
    private String privateKeyText;

    public RSAKeyPair(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        this.publicKey = rsaPublicKey;
        this.privateKey = rsaPrivateKey;
        this.publicKeyText = CodecUtil.encodeBase64(rsaPublicKey.getEncoded());
        this.privateKeyText = CodecUtil.encodeBase64(rsaPrivateKey.getEncoded());
    }
}