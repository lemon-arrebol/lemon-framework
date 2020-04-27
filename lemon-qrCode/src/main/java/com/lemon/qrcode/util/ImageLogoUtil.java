package com.lemon.qrcode.util;

import com.lemon.qrcode.entity.Logo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author lemon
 * @description 图片中插入LOGO
 * @date 2020-04-27 09:31
 */
@Slf4j
public class ImageLogoUtil {
    /**
     * @param imagePath
     * @param logo
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:30
     */
    public static byte[] insertLogoImageAsBytes(String imagePath, Logo logo) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imagePath);
        return ImageLogoUtil.insertLogoImageAsBytes(new File(imagePath), logo);
    }

    /**
     * @param imageFile
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:31
     */
    public static byte[] insertLogoImageAsBytes(File imageFile, Logo logo) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imageFile);
        BufferedImage image = ImageLogoUtil.insertLogoImage(imageFile, logo);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBytes
     * @param logo
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-26 23:32
     */
    public static byte[] insertLogoImageAsBytes(byte[] imageBytes, Logo logo) throws Exception {
        BufferedImage image = ImageLogoUtil.insertLogoImage(imageBytes, logo);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imagePath
     * @param logo
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:35
     */
    public static File insertLogoImageAsFile(String imagePath, Logo logo, String destPath) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imagePath);
        String filePath = ImageLogoUtil.insertLogoImageAsPath(imagePath, logo, destPath);
        return new File(filePath);
    }

    /**
     * @param imageFile
     * @param logo
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File insertLogoImageAsFile(File imageFile, Logo logo, String destPath) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imageFile);
        String filePath = ImageLogoUtil.insertLogoImageAsPath(imageFile, logo, destPath);
        return new File(filePath);
    }

    /**
     * @param imageBytes
     * @param logo
     * @param destPath
     * @return java.io.File
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static File insertLogoImageAsFile(byte[] imageBytes, Logo logo, String destPath) throws Exception {
        String filePath = ImageLogoUtil.insertLogoImageAsPath(imageBytes, logo, destPath);
        return new File(filePath);
    }

    /**
     * @param imagePath
     * @param logo
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:38
     */
    public static String insertLogoImageAsPath(String imagePath, Logo logo, String destPath) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imagePath);
        return ImageLogoUtil.insertLogoImageAsPath(new File(imagePath), logo, destPath);
    }

    /**
     * @param imageFile
     * @param logo
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String insertLogoImageAsPath(File imageFile, Logo logo, String destPath) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imageFile);
        BufferedImage image = ImageLogoUtil.insertLogoImage(imageFile, logo);
        String filePath = IOUtil.writeFile(image, logo.getFormatName(), destPath);
        return filePath;
    }

    /**
     * @param imageBytes
     * @param logo
     * @param destPath
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-26 23:39
     */
    public static String insertLogoImageAsPath(byte[] imageBytes, Logo logo, String destPath) throws Exception {
        BufferedImage image = ImageLogoUtil.insertLogoImage(imageBytes, logo);
        String filePath = IOUtil.writeFile(image, logo.getFormatName(), destPath);
        return filePath;
    }

    /**
     * @param imagePath
     * @param logo
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage insertLogoImage(String imagePath, Logo logo) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imagePath);
        return ImageLogoUtil.insertLogoImage(new File(imagePath), logo);
    }

    /**
     * @param imageFile
     * @param logo
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-26 22:30
     */
    public static BufferedImage insertLogoImage(File imageFile, Logo logo) throws Exception {
        IOUtil.checkAndSetFormatName(logo, imageFile);
        BufferedImage image = ImageIO.read(imageFile);
        ImageLogoUtil.insertLogoImage(image, logo);
        return image;
    }

    /**
     * @param imageBytes
     * @param logo
     * @return java.awt.image.BufferedImage
     * @description
     * @author lemon
     * @date 2020-04-27 09:42
     */
    public static BufferedImage insertLogoImage(byte[] imageBytes, Logo logo) throws Exception {
        BufferedImage image = ImageUtil.toBufferedImage(imageBytes);
        ImageLogoUtil.insertLogoImage(image, logo);
        return image;
    }

    /**
     * @param qrcodeImage 二维码图片
     * @param logo
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:37
     */
    public static void insertLogoImage(BufferedImage qrcodeImage, Logo logo) throws Exception {
        if (logo == null) {
            log.debug("没有指定Logo信息");
            return;
        }

        byte[] imageLogo = logo.getSrcIamge();
        Image logoImage;

        if (imageLogo == null || imageLogo.length == 0) {
            String imageLogoPath = logo.getSrcPath();

            if (StringUtils.isBlank(imageLogoPath)) {
                log.debug("未指定Logo字节数组且Logo文件也未指定！");
                return;
            }

            File file = new File(imageLogoPath);

            if (!file.exists()) {
                log.warn("Logo文件[{}]不存在！", imageLogoPath);
                return;
            }

            logoImage = ImageIO.read(new File(imageLogoPath));
        } else {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageLogo);
            logoImage = ImageIO.read(byteArrayInputStream);
        }

        logoImage = ImageRadiusUtil.cornerRadius((BufferedImage) logoImage, logo.getImageRadius());
        ImageLogoUtil.insertLogoImage(qrcodeImage, logoImage, logo);
    }

    /**
     * @param qrcodeImage 二维码图片
     * @param logoImage   LOGO图片Image
     * @param logo
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:39
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, Image logoImage, Logo logo) {
        if (logoImage == null) {
            return;
        }

        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);

        // 压缩LOGO
        if (logo.isCompress()) {
            if (width > logo.getWidth()) {
                width = logo.getWidth();
            }

            if (height > logo.getHeight()) {
                height = logo.getHeight();
            }

            // 调用缩放方法获取缩放后的图片。width 缩放后的宽度；height 缩放后的高度；hints 图像重采样算法的类型
            Image image = logoImage.getScaledInstance(width, height, logo.getHints());
            // 创建一个新的缓存图片
            BufferedImage bufferedImage = new BufferedImage(width, height, logo.getImageType());
            // 获取画笔
            Graphics graphics = bufferedImage.getGraphics();
            // 绘制缩小后的图，将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
            graphics.drawImage(image, logo.getX(), logo.getY(), logo.getObserver());
            // 释放资源
            graphics.dispose();

            logoImage = image;
        }

        int qrCodeWidth = qrcodeImage.getWidth();
        int qrCodeHeight = qrcodeImage.getHeight();
        int x = (qrCodeWidth - width) / 2;
        int y = (qrCodeHeight - height) / 2;
        // 插入LOGO
        Graphics2D graphics2D = qrcodeImage.createGraphics();
        graphics2D.drawImage(logoImage, x, y, width, height, logo.getObserver());
        graphics2D.setColor(logo.getColor());
        // 在图形和图像中实现混合和透明效果
        graphics2D.setComposite(logo.getComposite());
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, logo.getLogoShape().getArcw(), logo.getLogoShape().getArch());
        graphics2D.setStroke(new BasicStroke(logo.getLogoStroke().getWidth()));
        graphics2D.draw(shape);
        graphics2D.dispose();

        log.info("图片成功插入Logo, 二维码:宽度{}、高度{}, 插入位置:x {}, y {}", qrCodeWidth, qrCodeHeight, x, y);
    }
} 