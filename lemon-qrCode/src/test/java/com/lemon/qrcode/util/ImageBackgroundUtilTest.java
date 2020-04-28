package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.*;
import com.lemon.qrcode.strategy.ImageDynamicRenderStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class ImageBackgroundUtilTest {
    public static void main(String[] args) throws Exception {
        String backgroudImagePath = "/Users/houjuntao/qrCode/weChat.png";
        String imagePath = "/Users/houjuntao/qrCode/weChat.png";
        String destPath = "/Users/houjuntao/qrCode/backgroudImage";

        ImageBackground imageBackground = new ImageBackground();
        imageBackground.setSrcPath(backgroudImagePath);
        imageBackground.setDestPath(destPath);

        ImageBackgroundUtil.insertImageBackgroundAsFile(imageBackground, getQRCode());
    }

    public static BufferedImage getQRCode() throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String logoPath = "/Users/houjuntao/qrCode/weChat.png";
        String destPath = "/Users/houjuntao/qrCode/erWeiMa";

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
        qrCode.setImageRenderStrategy(new ImageDynamicRenderStrategy());

        Logo logo = new Logo();
        logo.setSrcPath(logoPath);
        logo.setCompress(true);
        logo.setImageRadius(imageRadius);

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love");

        QRCodeContainer qrCodeContainer = new QRCodeContainer();
        qrCodeContainer.setQrCode(qrCode);
        qrCodeContainer.setLogo(logo);
        qrCodeContainer.setDestPath(destPath);
        qrCodeContainer.setWaterMark(waterMark);

        return QRCodeUtil.generateQRCode(qrCodeContainer);
    }
}