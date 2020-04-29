package com.lemon.qrcode.util;

import com.lemon.qrcode.config.ImageRadius;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片圆角处理
 * @date Create by lemon on 2020-04-26 21:31
 */
@Slf4j
public class ImageRadiusUtil {
    /**
     * @param imagePath
     * @param imageRadius
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:30
     */
    public static byte[] cornerRadiusAsBytes(String imagePath, ImageRadius imageRadius) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imagePath);
        return ImageRadiusUtil.cornerRadiusAsBytes(new File(imagePath), imageRadius);
    }

    /**
     * @param imageFile
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:31
     */
    public static byte[] cornerRadiusAsBytes(File imageFile, ImageRadius imageRadius) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imageFile);
        BufferedImage image = ImageRadiusUtil.cornerRadius(imageFile, imageRadius);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBytes
     * @param imageRadius
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:32
     */
    public static byte[] cornerRadiusAsBytes(byte[] imageBytes, ImageRadius imageRadius) throws IOException {
        BufferedImage image = ImageRadiusUtil.cornerRadius(imageBytes, imageRadius);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imagePath
     * @param imageRadius
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:35
     */
    public static File cornerRadiusAsFile(String imagePath, ImageRadius imageRadius, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imagePath);
        String filePath = ImageRadiusUtil.cornerRadiusAsPath(imagePath, imageRadius, destPath);
        return new File(filePath);
    }

    /**
     * @param imageFile
     * @param imageRadius
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File cornerRadiusAsFile(File imageFile, ImageRadius imageRadius, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imageFile);
        String filePath = ImageRadiusUtil.cornerRadiusAsPath(imageFile, imageRadius, destPath);
        return new File(filePath);
    }

    /**
     * @param imageBytes
     * @param imageRadius
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File cornerRadiusAsFile(byte[] imageBytes, ImageRadius imageRadius, String destPath) throws IOException {
        String filePath = ImageRadiusUtil.cornerRadiusAsPath(imageBytes, imageRadius, destPath);
        return new File(filePath);
    }

    /**
     * @param imagePath
     * @param imageRadius
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static String cornerRadiusAsPath(String imagePath, ImageRadius imageRadius, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imagePath);
        return ImageRadiusUtil.cornerRadiusAsPath(new File(imagePath), imageRadius, destPath);
    }

    /**
     * @param imageFile
     * @param imageRadius
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String cornerRadiusAsPath(File imageFile, ImageRadius imageRadius, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imageFile);
        BufferedImage image = ImageRadiusUtil.cornerRadius(imageFile, imageRadius);
        String filePath = IOUtil.writeFile(image, imageRadius.getFormatName(), destPath);
        return filePath;
    }


    /**
     * @param imageBytes
     * @param imageRadius
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String cornerRadiusAsPath(byte[] imageBytes, ImageRadius imageRadius, String destPath) throws IOException {
        BufferedImage image = ImageRadiusUtil.cornerRadius(imageBytes, imageRadius);
        String filePath = IOUtil.writeFile(image, imageRadius.getFormatName(), destPath);
        return filePath;
    }

    /**
     * @param imagePath
     * @param imageRadius
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage cornerRadius(String imagePath, ImageRadius imageRadius) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imagePath);
        return ImageRadiusUtil.cornerRadius(new File(imagePath), imageRadius);
    }

    /**
     * @param imageFile
     * @param imageRadius
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage cornerRadius(File imageFile, ImageRadius imageRadius) throws IOException {
        IOUtil.checkAndSetFormatName(imageRadius, imageFile);
        BufferedImage image = ImageIO.read(imageFile);
        return ImageRadiusUtil.cornerRadius(image, imageRadius);
    }

    /**
     * @param imageBytes
     * @param imageRadius
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage cornerRadius(byte[] imageBytes, ImageRadius imageRadius) throws IOException {
        BufferedImage image = ImageUtil.toBufferedImage(imageBytes);
        return ImageRadiusUtil.cornerRadius(image, imageRadius);
    }

    /**
     * @param bufferedImage
     * @param imageRadius
     * @return java.awt.image.BufferedImage
     * @description 图片切圆角
     * @author lemon
     * @date 2020-04-25 22:11
     */
    public static BufferedImage cornerRadius(BufferedImage bufferedImage, ImageRadius imageRadius) {
        if (imageRadius == null) {
            log.debug("没有指定圆角信息");
            return bufferedImage;
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage radiusBufferedImage = new BufferedImage(width, height, imageRadius.getImageType());
        Graphics2D graphics2D = radiusBufferedImage.createGraphics();
        graphics2D.setRenderingHint(imageRadius.getHintKey(), imageRadius.getHintValue());
        graphics2D.setColor(imageRadius.getColor());
        graphics2D.fill(new RoundRectangle2D.Double(0, 0, width, height, imageRadius.getArcw(), imageRadius.getArch()));
        graphics2D.setComposite(imageRadius.getComposite());
        graphics2D.drawImage(bufferedImage, 0, 0, imageRadius.getObserver());
        graphics2D.dispose();
        log.info("图片成功切圆角");

        return radiusBufferedImage;
    }
}
