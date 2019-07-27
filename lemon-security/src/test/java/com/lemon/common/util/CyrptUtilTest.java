package com.lemon.common.util;

import com.lemon.common.algorithm.RSAKeyPair;
import com.lemon.common.algorithm.RSAKeyPairGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.Provider;
import java.security.Security;
import java.util.TreeSet;

public class CyrptUtilTest {
    String algorithm = "RSA";
    String signatureAlgorithm = "SHA1WithRSA";
    int keysize = 1024;
    String publicKeyText;
    String privateKeyText;
    String encryptText;
    String decryptText;

    @Before
    public void setUp() throws Exception {
        RSAKeyPair rsaKeyPair = RSAKeyPairGenerator.generateKeyPair(this.algorithm, this.keysize);
        publicKeyText = rsaKeyPair.getPublicKeyText();
        privateKeyText = rsaKeyPair.getPrivateKeyText();
        encryptText = "hjt love lr";

        TreeSet<String> algorithms = new TreeSet<>();

        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                if (service.getType().equals("Signature")) {
                    algorithms.add(service.getAlgorithm());
                }
            }
        }

        for (String algorithm : algorithms) {
            System.out.println(algorithm);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void encryptByPublicKey() throws Exception {
        CyrptUtil.encryptByPublicKey(this.algorithm, this.publicKeyText, this.encryptText);
    }

    @Test
    public void decryptByPublicKey() {
    }

    @Test
    public void encryptByPrivateKey() {
    }

    @Test
    public void decryptByPrivateKey() throws Exception {
        String cryptText = CyrptUtil.encryptByPublicKey(this.algorithm, this.publicKeyText, this.encryptText);
        cryptText = CyrptUtil.decryptByPrivateKey(this.algorithm, this.privateKeyText, cryptText);

        Assert.assertEquals(this.encryptText, cryptText);
    }

    @Test
    public void cryptByPublicKey() {
    }

    @Test
    public void cryptByPrivateKey() {
    }

    @Test
    public void cryptByKey() {
    }

    @Test
    public void generateKeyPair() {
    }

    @Test
    public void generatePublicKey() {
    }

    @Test
    public void generatePrivateKey() {
    }

    @Test
    public void checkCryptParatemer() {
    }

    @Test
    public void sign() throws Exception {
        System.out.println(CyrptUtil.sign(this.encryptText, this.algorithm, this.signatureAlgorithm, this.privateKeyText));
    }

    @Test
    public void sign1() {
    }

    @Test
    public void verify() throws Exception {
        String sign = CyrptUtil.sign(this.encryptText, this.algorithm, this.signatureAlgorithm, this.privateKeyText);
        boolean result = CyrptUtil.verify(this.encryptText, sign, this.algorithm, this.signatureAlgorithm, this.publicKeyText);

        Assert.assertTrue(result);
    }

    @Test
    public void verify1() {
    }
}