package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.*;
import com.lemon.qrcode.strategy.GoogleImageRenderStrategy;

import java.awt.*;
import java.security.SecureRandom;

public class QRCodeUtilTest {
    public static void main(String[] args) throws Exception {
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
        qrCode.setImageRenderStrategy(new GoogleImageRenderStrategy());

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

        System.out.println(QRCodeUtil.generateQRCodeAsPath(qrCodeContainer));
    }
}