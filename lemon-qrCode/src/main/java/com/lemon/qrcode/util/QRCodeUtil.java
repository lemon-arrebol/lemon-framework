package com.lemon.qrcode.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.lemon.qrcode.config.QRCode;
import com.lemon.qrcode.config.QRCodeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

/**
 * @author lemon
 * @description https://github.com/liuyueyi/quick-media
 * @date 2020-04-26 10:41
 */
@Slf4j
public class QRCodeUtil {
    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * @param qrCodeConfig
     * @return java.lang.String
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:09
     */
    public static File generateQRCodeAsFle(QRCodeConfig qrCodeConfig) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(qrCodeConfig.getDestPath()), "未指定二维码保存路径");
        String filePath = QRCodeUtil.generateQRCodeAsPath(qrCodeConfig);
        return new File(filePath);
    }

    /**
     * @param qrCodeConfig
     * @return java.lang.String
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:09
     */
    public static String generateQRCodeAsPath(QRCodeConfig qrCodeConfig) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(qrCodeConfig.getDestPath()), "未指定二维码保存路径");
        BufferedImage image = QRCodeUtil.generateQRCode(qrCodeConfig);
        String filePath = IOUtil.writeFile(image, qrCodeConfig.getFormatName(), qrCodeConfig.getDestPath());
        return filePath;
    }

    /**
     * @param qrCodeConfig
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 18:31
     */
    public static byte[] generateQRCodeBytes(QRCodeConfig qrCodeConfig) throws Exception {
        BufferedImage image = QRCodeUtil.generateQRCode(qrCodeConfig);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param qrCodeConfig
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:59
     */
    public static BufferedImage generateQRCode(QRCodeConfig qrCodeConfig) throws Exception {
        BufferedImage bufferedImage = QRCodeUtil.generateQRCodeNative(qrCodeConfig);

        // 插入图片
        if (qrCodeConfig.getLogo() != null) {
            ImageLogoUtil.insertLogoImage(bufferedImage, qrCodeConfig.getLogo());
        }

        // 插入水印
        if (qrCodeConfig.getWaterMark() != null && StringUtils.isNotBlank(qrCodeConfig.getWaterMark().getContent())) {
            ImageWaterMarkUtil.insertWaterMark(bufferedImage, qrCodeConfig.getWaterMark());
        }

        return bufferedImage;
    }

    /**
     * @param qrCodeConfig
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:56
     */
    public static BufferedImage generateQRCodeNative(QRCodeConfig qrCodeConfig) throws Exception {
        QRCode qrCode = qrCodeConfig.getQrCode();
        // 生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode.getContent(), qrCode.getBarcodeFormat(),
                qrCode.getWidth(), qrCode.getHeight(), qrCode.getHints());
        BufferedImage bufferedImage = qrCode.getImageRenderStrategy().render(bitMatrix, qrCode);
        qrCodeConfig.setQrBitMatrix(bitMatrix);
        qrCodeConfig.setQrBufferedImage(bufferedImage);
        return bufferedImage;
    }

    /**
     * @param path 二维码图片地址
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:02
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }

    /**
     * @param file 二维码图片
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:03
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        return QRCodeUtil.decode(image);
    }

    /**
     * @param qrcodes 二维码图片
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:02
     */
    public static String decode(byte[] qrcodes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrcodes);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        return QRCodeUtil.decode(image);
    }

    /**
     * @param image
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 20:34
     */
    public static String decode(BufferedImage image) throws NotFoundException {
        return QRCodeUtil.decode(image, DEFAULT_CHARSET);
    }

    /**
     * @param image
     * @param charset
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 20:34
     */
    public static String decode(BufferedImage image, String charset) throws NotFoundException {
        if (image == null) {
            return null;
        }

        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Map<DecodeHintType, Object> hints = Maps.newHashMap();
        hints.put(DecodeHintType.CHARACTER_SET, charset);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        String resultValue = result.getText();
        return resultValue;
    }

    /**
     * @param bitMatrix
     * @return int
     * @description 获取二维码位置探测图形宽度
     * @author lemon
     * @date 2020-04-28 23:52
     */
    public static int getPositionDetectPatternWidth(BitMatrix bitMatrix) {
        int length = 0;
        boolean flag = false;
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 二维码显示点
                if (bitMatrix.get(x, y) == true) {
                    flag = true;
                    length++;
                } else {
                    if (flag != false) {
                        return x;
                    }
                }
            }
        }

        log.info("二维码位置探测图形宽度%s", length);
        return length;
    }
} 