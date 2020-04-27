package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.ImageRadius;
import com.lemon.qrcode.entity.Logo;

public class ImageLogoUtilTest {
    public static void main(String[] args) throws Exception {
        String logoPath = "/Users/lemon/qrCode/weChat.png";
        String circularImgSavePath = "/Users/lemon/qrCode/logo";

        ImageRadius imageRadius = new ImageRadius();
        imageRadius.setArch(300);
        imageRadius.setArcw(300);

        Logo logo = new Logo();
        logo.setSrcPath(logoPath);
        logo.setCompress(true);
        logo.setImageRadius(imageRadius);

        ImageLogoUtil.insertLogoImageAsFile(logoPath, logo, circularImgSavePath);
    }
}