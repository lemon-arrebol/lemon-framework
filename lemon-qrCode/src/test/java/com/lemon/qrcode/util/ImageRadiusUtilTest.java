package com.lemon.qrcode.util;

import com.lemon.qrcode.config.ImageRadius;

import java.io.IOException;

public class ImageRadiusUtilTest {
    public static void main(String[] args) throws IOException {
        String srcFilePath = "/Users/lemon/qrCode/weChat.png";
        String circularImgSavePath = "/Users/lemon/qrCode/radius";

        ImageRadius imageRadius = new ImageRadius();
        imageRadius.setArch(300);
        imageRadius.setArcw(300);

        String destPath = ImageRadiusUtil.cornerRadiusAsPath(srcFilePath, imageRadius, circularImgSavePath);
        System.out.println(destPath);
    }
}