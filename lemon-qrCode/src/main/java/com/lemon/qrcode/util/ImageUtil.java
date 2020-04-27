package com.lemon.qrcode.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2020-04-26 22:55
 */
public class ImageUtil {
    /**
     * @param imageBytes
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 23:08
     */
    public static BufferedImage toBufferedImage(byte[] imageBytes) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        return image;
    }
}
