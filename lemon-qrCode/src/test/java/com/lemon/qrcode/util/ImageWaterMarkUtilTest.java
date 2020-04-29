package com.lemon.qrcode.util;

import com.lemon.qrcode.config.WaterMark;

import java.io.IOException;

public class ImageWaterMarkUtilTest {
    public static void main(String[] args) throws IOException {
        String srcFilePath = "/Users/lemon/qrCode/weChat.png";
        String circularImgSavePath = "/Users/lemon/qrCode/waterMark";

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love Love Love");

        ImageWaterMarkUtil.insertWaterMarkAsFile(srcFilePath, waterMark, circularImgSavePath);
    }
}