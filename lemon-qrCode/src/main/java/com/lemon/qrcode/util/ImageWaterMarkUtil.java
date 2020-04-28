package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.WaterMark;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lemon
 * @description 图片写入水印
 * @date 2020-04-27 09:32
 */
@Slf4j
public class ImageWaterMarkUtil {
    /**
     * @param imagePath
     * @param waterMark
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:30
     */
    public static byte[] insertWaterMarkAsBytes(String imagePath, WaterMark waterMark) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imagePath);
        return ImageWaterMarkUtil.insertWaterMarkAsBytes(new File(imagePath), waterMark);
    }

    /**
     * @param imageFile
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:31
     */
    public static byte[] insertWaterMarkAsBytes(File imageFile, WaterMark waterMark) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imageFile);
        BufferedImage image = ImageWaterMarkUtil.insertWaterMark(imageFile, waterMark);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBytes
     * @param waterMark
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:32
     */
    public static byte[] insertWaterMarkAsBytes(byte[] imageBytes, WaterMark waterMark) throws IOException {
        BufferedImage image = ImageWaterMarkUtil.insertWaterMark(imageBytes, waterMark);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imagePath
     * @param waterMark
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:35
     */
    public static File insertWaterMarkAsFile(String imagePath, WaterMark waterMark, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imagePath);
        String filePath = ImageWaterMarkUtil.insertWaterMarkAsPath(imagePath, waterMark, destPath);
        return new File(filePath);
    }

    /**
     * @param imageFile
     * @param waterMark
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File insertWaterMarkAsFile(File imageFile, WaterMark waterMark, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imageFile);
        String filePath = ImageWaterMarkUtil.insertWaterMarkAsPath(imageFile, waterMark, destPath);
        return new File(filePath);
    }

    /**
     * @param imageBytes
     * @param waterMark
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File insertWaterMarkAsFile(byte[] imageBytes, WaterMark waterMark, String destPath) throws IOException {
        String filePath = ImageWaterMarkUtil.insertWaterMarkAsPath(imageBytes, waterMark, destPath);
        return new File(filePath);
    }

    /**
     * @param imagePath
     * @param waterMark
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static String insertWaterMarkAsPath(String imagePath, WaterMark waterMark, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imagePath);
        return ImageWaterMarkUtil.insertWaterMarkAsPath(new File(imagePath), waterMark, destPath);
    }

    /**
     * @param imageFile
     * @param waterMark
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String insertWaterMarkAsPath(File imageFile, WaterMark waterMark, String destPath) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imageFile);
        BufferedImage image = ImageWaterMarkUtil.insertWaterMark(imageFile, waterMark);
        String filePath = IOUtil.writeFile(image, waterMark.getFormatName(), destPath);
        return filePath;
    }

    /**
     * @param imageBytes
     * @param waterMark
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String insertWaterMarkAsPath(byte[] imageBytes, WaterMark waterMark, String destPath) throws IOException {
        BufferedImage image = ImageWaterMarkUtil.insertWaterMark(imageBytes, waterMark);
        String filePath = IOUtil.writeFile(image, waterMark.getFormatName(), destPath);
        return filePath;
    }

    /**
     * @param imagePath
     * @param waterMark
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage insertWaterMark(String imagePath, WaterMark waterMark) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imagePath);
        return ImageWaterMarkUtil.insertWaterMark(new File(imagePath), waterMark);
    }

    /**
     * @param imageFile
     * @param waterMark
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage insertWaterMark(File imageFile, WaterMark waterMark) throws IOException {
        IOUtil.checkAndSetFormatName(waterMark, imageFile);
        BufferedImage image = ImageIO.read(imageFile);
        ImageWaterMarkUtil.insertWaterMark(image, waterMark);
        return image;
    }

    /**
     * @param imageBytes
     * @param waterMark
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-27 09:42
     */
    public static BufferedImage insertWaterMark(byte[] imageBytes, WaterMark waterMark) throws IOException {
        BufferedImage image = ImageUtil.toBufferedImage(imageBytes);
        ImageWaterMarkUtil.insertWaterMark(image, waterMark);
        return image;
    }

    /**
     * @param qrcodeImage
     * @param waterMark
     * @return void
     * @description 插入水印
     * @author lemon
     * @date 2020-04-26 09:42
     */
    public static void insertWaterMark(BufferedImage qrcodeImage, WaterMark waterMark) {
        if (waterMark == null || StringUtils.isBlank(waterMark.getContent())) {
            log.debug("没有指定水印信息");
            return;
        }

        int qrCodeWidth = qrcodeImage.getWidth();
        int qrCodeHeight = qrcodeImage.getHeight();

        // 获取画笔，插入水印
        Graphics2D graphics2D = qrcodeImage.createGraphics();
        // 设置背景颜色
        graphics2D.setBackground(waterMark.getBackground().get());
        // 设置“抗锯齿”的属性
        graphics2D.setRenderingHint(waterMark.getHintKey(), waterMark.getHintValue());
        // 设置字体类型和大小
        graphics2D.setFont(waterMark.getFont());
        // 设置字体颜色
        graphics2D.setColor(waterMark.getColor().get());
        // 设置透明度
        graphics2D.setComposite(waterMark.getComposite());

        int x = waterMark.getX();
        int y = waterMark.getY();

        if (x > qrCodeWidth) {
            x = qrCodeWidth / 2;
        }

        if (y == 0 || y > qrCodeHeight) {
            y = qrCodeHeight / 2;
        }

        // 添加文字
        graphics2D.drawString(waterMark.getContent(), x, y);
        graphics2D.dispose();

        log.info("图片成功写入水印内容{}, 二维码:宽度{}、高度{}, 插入位置:x {}, y {}", waterMark.getContent(), qrCodeWidth, qrCodeHeight, x, y);
    }
} 