package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.Logo;
import com.lemon.qrcode.entity.QRCode;
import com.lemon.qrcode.entity.QRCodeContainer;
import com.lemon.qrcode.entity.WaterMark;

public class QRCodeEntityUtilTest {
    public static void main(String[] args) throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String logoPath = "/Users/lemon/qrCode/logo.png";
        String destPath = "/Users/lemon/qrCode";

        QRCode qrCode = new QRCode();
        qrCode.setContent(text);

        Logo logo = new Logo();
        logo.setPath(logoPath);
        logo.setCompress(true);

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love");

        QRCodeContainer qrCodeContainer = new QRCodeContainer();
        qrCodeContainer.setQrCode(qrCode);
        qrCodeContainer.setLogo(logo);
        qrCodeContainer.setDestPath(destPath);
        qrCodeContainer.setWaterMark(waterMark);

        System.out.println(QRCodeEntityUtil.encode(qrCodeContainer));
    }
}