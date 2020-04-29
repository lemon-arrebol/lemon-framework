package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.WaterMark;

import java.io.IOException;

public class ImageWaterMarkUtilTest {
    public static void main(String[] args) throws IOException {
        String srcFilePath = "/Users/houjuntao/qrCode/weChat.png";
        String circularImgSavePath = "/Users/houjuntao/qrCode/waterMark";

        WaterMark waterMark = new WaterMark();
        waterMark.setContent("Love Love Love");

        ImageWaterMarkUtil.insertWaterMarkAsFile(srcFilePath, waterMark, circularImgSavePath);
    }
}