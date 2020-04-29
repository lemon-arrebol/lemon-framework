package com.lemon.qrcode.util;

import com.lemon.qrcode.config.ImageAlpha;

import java.awt.*;

public class ImageAlphaUtilTest {
    public static void main(String[] args) throws Exception {
        String backgroudImagePath = "/Users/lemon/qrCode/backgroudImage.jpeg";
        String destPath = "/Users/lemon/qrCode/alpha";

        ImageAlpha imageAlpha = new ImageAlpha();
        imageAlpha.setSrcPath(backgroudImagePath);
        imageAlpha.setDestPath(destPath);
        imageAlpha.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.6f));

        ImageAlphaUtil.insertImageAlphaAsFile(imageAlpha);
    }
}