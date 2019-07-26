package com.lemon.common.algorithm;

import com.lemon.common.algorithm.RSAKeyPair;
import com.lemon.common.algorithm.RSAKeyPairGenerator;
import com.lemon.common.util.CodecUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyPairGeneratorTest {
    String algorithm = "RSA";
    int keysize = 1024;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAKeyPair rsaKeyPair = RSAKeyPairGenerator.generateKeyPair(this.algorithm, this.keysize);
        System.out.println("PublicKeyText======" + rsaKeyPair.getPublicKeyText());
        System.out.println("PrivateKeyText======" + rsaKeyPair.getPrivateKeyText());
        System.out.println("================================================");
        System.out.println();

        System.out.println("PrivateKey Modulus======" + rsaKeyPair.getPrivateKey().getModulus());
        System.out.println("PrivateKey PrivateExponent======" + rsaKeyPair.getPrivateKey().getPrivateExponent());
        System.out.println("PrivateKey Format======" + rsaKeyPair.getPrivateKey().getFormat());
        System.out.println("PrivateKey Algorithm======" + rsaKeyPair.getPrivateKey().getAlgorithm());
        System.out.println("PrivateKey Encoded======" + CodecUtil.encodeBase64(rsaKeyPair.getPrivateKey().getEncoded()));
        System.out.println("================================================");
        System.out.println();

        System.out.println("PublicKey Modulus======" + rsaKeyPair.getPublicKey().getModulus());
        System.out.println("PublicKey PublicExponent======" + rsaKeyPair.getPublicKey().getPublicExponent());
        System.out.println("PublicKey Format======" + rsaKeyPair.getPublicKey().getFormat());
        System.out.println("PublicKey Algorithm======" + rsaKeyPair.getPublicKey().getAlgorithm());
        System.out.println("PublicKey Encoded======" + CodecUtil.encodeBase64(rsaKeyPair.getPublicKey().getEncoded()));
        System.out.println("================================================");
        System.out.println();

        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(rsaKeyPair.getPrivateKey().getModulus(), rsaKeyPair.getPrivateKey().getPrivateExponent());
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        System.out.println("PrivateKey Modulus======" + rsaPrivateKey.getModulus());
        System.out.println("PrivateKey Encoded======" + CodecUtil.encodeBase64(rsaPrivateKey.getEncoded()));
        System.out.println("PrivateKey Encoded equals======" + CodecUtil.encodeBase64(rsaPrivateKey.getEncoded()).equals(CodecUtil.encodeBase64(rsaKeyPair.getPrivateKey().getEncoded())));
        System.out.println("================================================");
        System.out.println();

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(rsaKeyPair.getPublicKey().getModulus(), rsaKeyPair.getPublicKey().getPublicExponent());
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        System.out.println("PublicKey Modulus======" + rsaPublicKey.getModulus());
        System.out.println("PublicKey Encoded======" + CodecUtil.encodeBase64(rsaPublicKey.getEncoded()));
        System.out.println("PublicKey Encoded equals======" + CodecUtil.encodeBase64(rsaPublicKey.getEncoded()).equals(CodecUtil.encodeBase64(rsaKeyPair.getPublicKey().getEncoded())));
    }
}