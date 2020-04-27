package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.*;

public class QRCodeUtilTest {
    public static void main(String[] args) throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String logoPath = "/Users/lemon/qrCode/weChat.png";
        String destPath = "/Users/lemon/qrCode";

        ImageRadius imageRadius = new ImageRadius();
        imageRadius.setArch(300);
        imageRadius.setArcw(300);

        QRCode qrCode = new QRCode();
        qrCode.setContent(text);

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

        System.out.println(QRCodeUtil.encode(qrCodeContainer));
    }
}