package com.lemon.common.util;

import com.google.common.base.Preconditions;
import com.lemon.common.exception.EncryptException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author lemon
 * @version 1.0
 * @description: 加解密工具类: 公钥加密、私钥解密; 私钥签名、公钥验签。
 * <p>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全。
 * <p>
 * 如果我们在实际应用中如果只使用加密和签名中的一种可能带来不同的问题。如果我们在消息传递的过程中只对数据加密不进行签名，
 * 这样虽然可保证被截获的消息 不会泄露，但是可以利用截获的公钥，将假信息进行加密，然后传递到B。如果只对信息签名，
 * 这样虽然可以防止消息被篡改，但是不能防止消息泄露，所以我们在实际项目中需要根据实际需求进行使用。
 * @date Create by lemon on 2019-07-25 18:07
 */
@Slf4j
public class CyrptUtil {
    /**
     * @param
     * @return
     * @description 公钥加密
     * @author lemon
     * @date 2019-07-25 18:22
     */
    public static String encryptByPublicKey(String algorithm, String publicKeyText, String encryptText) throws Exception {
        byte[] result = CyrptUtil.cryptByPublicKey(algorithm, publicKeyText, CodecUtil.decodeUtf8(encryptText), Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64String(result);
    }

    /**
     * @param
     * @return
     * @description 公钥解密
     * @author lemon
     * @date 2019-07-25 18:22
     */
    public static String decryptByPublicKey(String algorithm, String publicKeyText, String decryptText) throws Exception {
        byte[] result = CyrptUtil.cryptByPublicKey(algorithm, publicKeyText, CodecUtil.decodeBase64(decryptText), Cipher.DECRYPT_MODE);
        return new String(result);
    }

    /**
     * @param
     * @return
     * @description 私钥加密
     * @author lemon
     * @date 2019-07-25 18:23
     */
    public static String encryptByPrivateKey(String algorithm, String privateKeyText, String encryptText) throws Exception {
        byte[] result = CyrptUtil.cryptByPrivateKey(algorithm, privateKeyText, CodecUtil.decodeUtf8(encryptText), Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64String(result);
    }

    /**
     * @param
     * @return
     * @description 私钥解密
     * @author lemon
     * @date 2019-07-25 18:23
     */
    public static String decryptByPrivateKey(String algorithm, String privateKeyText, String decryptText) throws Exception {
        byte[] result = CyrptUtil.cryptByPrivateKey(algorithm, privateKeyText, CodecUtil.decodeBase64(decryptText), Cipher.DECRYPT_MODE);
        return CodecUtil.encodeUtf8(result);
    }

    /**
     * @param
     * @return
     * @description 公钥加密/解密
     * @author lemon
     * @date 2019-07-25 18:23
     */
    public static byte[] cryptByPublicKey(String algorithm, String publicKeyText, byte[] cryptTextArray, int cryptMode) throws Exception {
        CyrptUtil.checkCryptParatemer(algorithm, publicKeyText, cryptTextArray, cryptMode);

        PublicKey publicKey = CyrptUtil.generatePublicKey(algorithm, publicKeyText);
        byte[] result = CyrptUtil.cryptByKey(publicKey, cryptTextArray, cryptMode);
        return result;
    }

    /**
     * @param
     * @return
     * @description 私钥加密/解密
     * @author lemon
     * @date 2019-07-25 18:23
     */
    public static byte[] cryptByPrivateKey(String algorithm, String privateKeyText, byte[] cryptTextArray, int cryptMode) throws Exception {
        CyrptUtil.checkCryptParatemer(algorithm, privateKeyText, cryptTextArray, cryptMode);

        PrivateKey privateKey = CyrptUtil.generatePrivateKey(algorithm, privateKeyText);
        byte[] result = CyrptUtil.cryptByKey(privateKey, cryptTextArray, cryptMode);
        return result;
    }

    /**
     * @param
     * @return
     * @description 加解密数据
     * @author lemon
     * @date 2019-07-25 23:46
     */
    public static byte[] cryptByKey(Key privateKey, byte[] cryptTextArray, int cryptMode) throws Exception {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(cryptMode, privateKey);
        byte[] result = CyrptUtil.cryptData(cipher, cryptMode, cryptTextArray, 1024);
        return result;
    }

    /**
     * @param
     * @return
     * @description 生成公钥
     * @author lemon
     * @date 2019-07-25 22:38
     */
    public static PublicKey generatePublicKey(String algorithm, String publicKeyText) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(publicKeyText), "PublicKeyText not allowed to be empty");

        // 密钥材料转换
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(CodecUtil.decodeBase64(publicKeyText));
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);

        return publicKey;
    }

    /**
     * @param
     * @return
     * @description 生成私钥
     * @author lemon
     * @date 2019-07-25 22:38
     */
    public static PrivateKey generatePrivateKey(String algorithm, String privateKeyText) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(privateKeyText), "PrivateKey not allowed to be empty");

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(CodecUtil.decodeBase64(privateKeyText));
        // algorithm 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 取私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);

        return privateKey;
    }

    /**
     * @param
     * @return
     * @description 校验加密/解密参数
     * @author lemon
     * @date 2019-07-25 22:52
     */
    public static void checkCryptParatemer(String algorithm, String keyText, byte[] cryptText, int cryptMode) {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(keyText), "Key not allowed to be empty");
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(cryptText), "CryptText not allowed to be empty");
        Preconditions.checkArgument(cryptMode == Cipher.ENCRYPT_MODE || cryptMode == Cipher.DECRYPT_MODE, String.format("The parameter cryptMode is invalid, allows the value to be [%s, %s]", Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE));
    }

    /**
     * @param
     * @return
     * @description 生成签名
     * @author lemon
     * @date 2019-07-26 12:43
     */
    public static String sign(String data, String algorithm, String signatureAlgorithm, String privateKeyText) throws Exception {
        return CyrptUtil.sign(CodecUtil.decodeUtf8(data), algorithm, signatureAlgorithm, privateKeyText);
    }

    /**
     * @param
     * @return
     * @description 生成签名
     * @author lemon
     * @date 2019-07-26 11:14
     */
    public static String sign(byte[] data, String algorithm, String signatureAlgorithm, String privateKeyText) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(signatureAlgorithm), "Signature Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(privateKeyText), "PrivateKey not allowed to be empty");

        // 取私钥对象
        PrivateKey privateKey = CyrptUtil.generatePrivateKey(algorithm, privateKeyText);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);

        return CodecUtil.encodeBase64(signature.sign());
    }

    /**
     * @param
     * @return
     * @description 验证签名
     * @author lemon
     * @date 2019-07-26 12:44
     */
    public static boolean verify(String data, String sign, String algorithm, String signatureAlgorithm, String publicKeyText) throws Exception {
        return CyrptUtil.verify(CodecUtil.decodeUtf8(data), sign, algorithm, signatureAlgorithm, publicKeyText);
    }

    /**
     * @param
     * @return
     * @description 验证签名
     * @author lemon
     * @date 2019-07-26 11:33
     */
    public static boolean verify(byte[] data, String sign, String algorithm, String signatureAlgorithm, String publicKeyText) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(algorithm), "Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(signatureAlgorithm), "Signature Must specify an algorithm");
        Preconditions.checkArgument(StringUtils.isNotBlank(publicKeyText), "PublicKey not allowed to be empty");

        // 取公钥对象
        PublicKey pubKey = CyrptUtil.generatePublicKey(algorithm, publicKeyText);
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否有效
        return signature.verify(CodecUtil.decodeBase64(sign));
    }

    /**
     * @param
     * @return
     * @description 分段加解密数据
     * @author lemon
     * @date 2019-07-25 23:37
     */
    private static byte[] cryptData(Cipher cipher, int cryptMode, byte[] cryptTextArray, int keySize) throws IOException {
        int maxBlock;

        if (cryptMode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff;
        int i = 0;
        int offSet = 0;

        try {
            // 对数据分段加解密
            while (cryptTextArray.length > offSet) {
                if (cryptTextArray.length - offSet > maxBlock) {
                    buff = cipher.doFinal(cryptTextArray, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(cryptTextArray, offSet, cryptTextArray.length - offSet);
                }

                outputStream.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new EncryptException(String.format("加解密阀值为[%s]的数据时发生异常", maxBlock), e);
        }

        byte[] result = outputStream.toByteArray();
        outputStream.close();

        return result;
    }
}
