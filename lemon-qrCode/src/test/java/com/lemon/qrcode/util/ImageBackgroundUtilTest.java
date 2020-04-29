package com.lemon.qrcode.util;

import com.lemon.qrcode.config.*;
import com.lemon.qrcode.strategy.DefaultImageRenderStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class ImageBackgroundUtilTest {
    public static void main(String[] args) throws Exception {
        String backgroudImagePath = "/Users/lemon/qrCode/weChat.png";
        String imagePath = "/Users/lemon/qrCode/weChat.png";
        String destPath = "/Users/lemon/qrCode/backgroudImage";

        ImageBackground imageBackground = new ImageBackground();
        imageBackground.setSrcPath(backgroudImagePath);
        imageBackground.setDestPath(destPath);

        ImageBackgroundUtil.insertImageBackgroundAsFile(imageBackground, getQRCode());
    }

    public static BufferedImage getQRCode() throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String logoPath = "/Users/lemon/qrCode/weChat.png";
        String destPath = "/Users/lemon/qrCode/erWeiMa";

        ImageRadius imageRadius = new ImageRadius();
        imageRadius.setArch(300);
        imageRadius.setArcw(300);

        QRCode qrCode = new QRCode();
        qrCode.setContent(text);
        qrCode.setOnColor(() -> {
            SecureRandom random = new SecureRandom();
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            int alpha = random.nextInt(255);
            System.out.println(String.format("red %s, green %s, blue %s, alpha %s", red, green, blue, alpha));
            return new Color(red, green, blue, alpha);
        });
        qrCode.setImageRenderStrategy(new DefaultImageRenderStrategy());

        Logo logo = new Logo();
        logo.setSrcPath(logoPath);
        logo.setCompress(true);
        logo.setImageRadius(imageRadius);

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love");

        QRCodeConfig qrCodeConfig = new QRCodeConfig();
        qrCodeConfig.setQrCode(qrCode);
        qrCodeConfig.setLogo(logo);
        qrCodeConfig.setDestPath(destPath);
        qrCodeConfig.setWaterMark(waterMark);

        return QRCodeUtil.generateQRCode(qrCodeConfig);
    }
}