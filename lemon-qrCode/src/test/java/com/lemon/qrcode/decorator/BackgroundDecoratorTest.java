package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.*;
import com.lemon.qrcode.strategy.DefaultImageRenderStrategy;
import com.lemon.qrcode.util.IOUtil;

import java.awt.*;
import java.security.SecureRandom;

public class BackgroundDecoratorTest {
    public static void main(String[] args) throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String backgroudImagePath = "/Users/lemon/qrCode/weChat.png";
        String logoPath = "/Users/lemon/qrCode/weChat.png";
        String destPath = "/Users/lemon/qrCode/backgroudImage";

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
            return new Color(red, green, blue, alpha);
        });

        qrCode.setImageRenderStrategy(new DefaultImageRenderStrategy());

        Logo logo = new Logo();
        logo.setSrcPath(logoPath);
        logo.setCompress(true);
        logo.setImageRadius(imageRadius);

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love");

        ImageBackground imageBackground = new ImageBackground();
        imageBackground.setSrcPath(logoPath);
        imageBackground.setDestPath(destPath);

        QRCodeConfig qrCodeConfig = new QRCodeConfig();
        qrCodeConfig.setQrCode(qrCode);
        qrCodeConfig.setLogo(logo);
        qrCodeConfig.setDestPath(destPath);
        qrCodeConfig.setWaterMark(waterMark);
        qrCodeConfig.setImageBackground(imageBackground);

        QRCodeGenerator defaultQRCodeGenerator = new DefaultQRCodeGenerator();
        QRCodeGenerator waterMarkDecorator = new WaterMarkDecorator(defaultQRCodeGenerator);
        LogoDecorator logoDecorator = new LogoDecorator(waterMarkDecorator);
        BackgroundDecorator backgroundDecorator = new BackgroundDecorator(logoDecorator);
        backgroundDecorator.generator(qrCodeConfig);

        IOUtil.writeFile(qrCodeConfig.getQrBufferedImage(), qrCodeConfig.getFormatName(), qrCodeConfig.getDestPath());
    }
}