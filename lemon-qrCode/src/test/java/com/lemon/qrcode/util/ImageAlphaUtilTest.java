package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.ImageAlpha;

import java.awt.*;

public class ImageAlphaUtilTest {
    public static void main(String[] args) throws Exception {
        String backgroudImagePath = "/Users/houjuntao/qrCode/backgroudImage.jpeg";
        String destPath = "/Users/houjuntao/qrCode/alpha";

        ImageAlpha imageAlpha = new ImageAlpha();
        imageAlpha.setSrcPath(backgroudImagePath);
        imageAlpha.setDestPath(destPath);
        imageAlpha.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.6f));

        ImageAlphaUtil.insertImageAlphaAsFile(imageAlpha);
    }
}