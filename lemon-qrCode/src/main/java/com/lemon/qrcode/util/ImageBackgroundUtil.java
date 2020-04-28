package com.lemon.qrcode.util;

import com.google.common.base.Preconditions;
import com.lemon.qrcode.entity.ImageAlpha;
import com.lemon.qrcode.entity.ImageBackground;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author lemon
 * @description 添加背景图片
 * @date 2020-04-27 09:31
 */
@Slf4j
public class ImageBackgroundUtil {
    /**
     * @param imageBackground
     * @param imagePath
     * @return byte[]
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:15
     */
    public static byte[] insertImageBackgroundAsBytes(ImageBackground imageBackground, String imagePath) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imagePath);
        return ImageBackgroundUtil.insertImageBackgroundAsBytes(imageBackground, new File(imagePath));
    }

    /**
     * @param imageBackground
     * @param imageFile
     * @return byte[]
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:24
     */
    public static byte[] insertImageBackgroundAsBytes(ImageBackground imageBackground, File imageFile) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imageFile);
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, imageFile);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBackground
     * @param imageBytes
     * @return byte[]
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:24
     */
    public static byte[] insertImageBackgroundAsBytes(ImageBackground imageBackground, byte[] imageBytes) throws Exception {
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, imageBytes);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBackground
     * @param bufferedImage
     * @return byte[]
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:48
     */
    public static byte[] insertImageBackgroundAsBytes(ImageBackground imageBackground, BufferedImage bufferedImage) throws Exception {
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, bufferedImage);
        return IOUtil.toByteArray(image);
    }

    /**
     * @param imageBackground
     * @param imagePath
     * @return java.io.File
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static File insertImageBackgroundAsFile(ImageBackground imageBackground, String imagePath) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imagePath);
        String filePath = ImageBackgroundUtil.insertImageBackgroundAsPath(imageBackground, imagePath);
        return new File(filePath);
    }

    /**
     * @param imageBackground
     * @param imageFile
     * @return java.io.File
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:24
     */
    public static File insertImageBackgroundAsFile(ImageBackground imageBackground, File imageFile) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imageFile);
        String filePath = ImageBackgroundUtil.insertImageBackgroundAsPath(imageBackground, imageFile);
        return new File(filePath);
    }

    /**
     * @param imageBackground
     * @param imageBytes
     * @return java.io.File
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static File insertImageBackgroundAsFile(ImageBackground imageBackground, byte[] imageBytes) throws Exception {
        String filePath = ImageBackgroundUtil.insertImageBackgroundAsPath(imageBackground, imageBytes);
        return new File(filePath);
    }

    /**
     * @param imageBackground
     * @param bufferedImage
     * @return java.io.File
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:49
     */
    public static File insertImageBackgroundAsFile(ImageBackground imageBackground, BufferedImage bufferedImage) throws Exception {
        String filePath = ImageBackgroundUtil.insertImageBackgroundAsPath(imageBackground, bufferedImage);
        return new File(filePath);
    }

    /**
     * @param imageBackground
     * @param imagePath
     * @return java.lang.String
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static String insertImageBackgroundAsPath(ImageBackground imageBackground, String imagePath) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imagePath);
        return ImageBackgroundUtil.insertImageBackgroundAsPath(imageBackground, new File(imagePath));
    }

    /**
     * @param imageBackground
     * @param imageFile
     * @return java.lang.String
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static String insertImageBackgroundAsPath(ImageBackground imageBackground, File imageFile) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imageFile);
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, imageFile);
        String filePath = IOUtil.writeFile(image, imageBackground.getFormatName(), imageBackground.getDestPath());
        return filePath;
    }

    /**
     * @param imageBackground
     * @param imageBytes
     * @return java.lang.String
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static String insertImageBackgroundAsPath(ImageBackground imageBackground, byte[] imageBytes) throws Exception {
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, imageBytes);
        String filePath = IOUtil.writeFile(image, imageBackground.getFormatName(), imageBackground.getDestPath());
        return filePath;
    }

    /**
     * @param imageBackground
     * @param bufferedImage
     * @return java.lang.String
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:49
     */
    public static String insertImageBackgroundAsPath(ImageBackground imageBackground, BufferedImage bufferedImage) throws Exception {
        BufferedImage image = ImageBackgroundUtil.insertImageBackground(imageBackground, bufferedImage);
        String filePath = IOUtil.writeFile(image, imageBackground.getFormatName(), imageBackground.getDestPath());
        return filePath;
    }

    /**
     * @param imageBackground
     * @param imagePath
     * @return java.awt.image.BufferedImage
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:26
     */
    public static BufferedImage insertImageBackground(ImageBackground imageBackground, String imagePath) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imagePath);
        return ImageBackgroundUtil.insertImageBackground(imageBackground, new File(imagePath));
    }

    /**
     * @param imageBackground * @param imageFile
     * @return java.awt.image.BufferedImage
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:26
     */
    public static BufferedImage insertImageBackground(ImageBackground imageBackground, File imageFile) throws Exception {
        IOUtil.checkAndSetFormatName(imageBackground, imageFile);
        BufferedImage image = ImageIO.read(imageFile);
        image = ImageBackgroundUtil.insertImageBackground(imageBackground, image);
        return image;
    }

    /**
     * @param imageBackground
     * @param imageBytes
     * @return java.awt.image.BufferedImage
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:26
     */
    public static BufferedImage insertImageBackground(ImageBackground imageBackground, byte[] imageBytes) throws Exception {
        BufferedImage image = ImageUtil.toBufferedImage(imageBytes);
        image = ImageBackgroundUtil.insertImageBackground(imageBackground, image);
        return image;
    }

    /**
     * @param image
     * @param imageBackground 背景图片
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:37
     */
    public static BufferedImage insertImageBackground(ImageBackground imageBackground, BufferedImage image) throws Exception {
        Preconditions.checkArgument(imageBackground != null, "没有指定背景图片信息");

        byte[] imageBackgroundBytes = imageBackground.getSrcIamge();
        BufferedImage bgBufferedImage;

        if (imageBackgroundBytes == null || imageBackgroundBytes.length == 0) {
            String imageImageBackgroundPath = imageBackground.getSrcPath();

            if (StringUtils.isBlank(imageImageBackgroundPath)) {
                throw new IllegalArgumentException("未指定背景图片字节数组且背景图片文件也未指定！");
            }

            File file = new File(imageImageBackgroundPath);

            if (!file.exists()) {
                throw new IllegalArgumentException(String.format("背景图片[%s]不存在！", imageImageBackgroundPath));
            }

            bgBufferedImage = ImageIO.read(new File(imageImageBackgroundPath));
        } else {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBackgroundBytes);
            bgBufferedImage = ImageIO.read(byteArrayInputStream);
        }

        ImageAlpha imageAlpha = new ImageAlpha();
        imageAlpha.setSrcIamge(IOUtil.toByteArray(bgBufferedImage));
        imageAlpha.setComposite(imageBackground.getComposite());
        imageAlpha.setColor(imageBackground.getColor());
        bgBufferedImage = ImageAlphaUtil.insertImageAlpha(imageAlpha);
        ImageBackgroundUtil.insertImageBackground(bgBufferedImage, imageBackground, image);
        return bgBufferedImage;
    }

    /**
     * @param bgBufferedImage 背景图片
     * @param imageBackground
     * @param image
     * @return void
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:55
     */
    private static void insertImageBackground(BufferedImage bgBufferedImage, ImageBackground imageBackground, BufferedImage image) {
        if (bgBufferedImage == null) {
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int bgWidth = bgBufferedImage.getWidth();
        int bgHeight = bgBufferedImage.getHeight();
        // 距离背景图片x边的距离，居中显示
        int disx = (bgWidth - width - imageBackground.getX()) / 2;
        // 距离y边距离
        int disy = (bgHeight - height - imageBackground.getY()) / 2;

        Graphics2D graphics2D = bgBufferedImage.createGraphics();
        graphics2D.drawImage(image, disx, disy, width, height, imageBackground.getObserver());
        graphics2D.setColor(imageBackground.getColor().get());
        graphics2D.dispose();

        log.info("成功插入背景图, 背景图片:宽度{}、高度{}, 图片:宽度{}、高度{}, 插入位置:x {}, y {}", bgWidth, bgHeight, width, height, disx, disy);
    }
} 