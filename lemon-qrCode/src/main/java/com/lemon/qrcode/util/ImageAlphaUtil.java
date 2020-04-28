package com.lemon.qrcode.util;

import com.google.common.base.Preconditions;
import com.lemon.qrcode.entity.ImageAlpha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author lemon
 * @description 图片改变透明度
 * @date 2020-04-27 09:31
 */
@Slf4j
public class ImageAlphaUtil {
    /**
     * @param imageAlpha
     * @return byte[]
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:15
     */
    public static byte[] insertImageAlphaAsBytes(ImageAlpha imageAlpha) throws Exception {
        return ImageAlphaUtil.insertImageAlphaAsBytes(imageAlpha);
    }

    /**
     * @param imageAlpha
     * @return java.io.File
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static File insertImageAlphaAsFile(ImageAlpha imageAlpha) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(imageAlpha.getDestPath()), "保存地址不允许为空");
        String filePath = ImageAlphaUtil.insertImageAlphaAsPath(imageAlpha);
        return new File(filePath);
    }

    /**
     * @param imageAlpha
     * @return java.lang.String
     * @description
     * @author houjuntao
     * @date 2020-04-28 19:25
     */
    public static String insertImageAlphaAsPath(ImageAlpha imageAlpha) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(imageAlpha.getDestPath()), "保存地址不允许为空");
        BufferedImage image = ImageAlphaUtil.insertImageAlpha(imageAlpha);
        String filePath = IOUtil.writeFile(image, imageAlpha.getFormatName(), imageAlpha.getDestPath());
        return filePath;
    }

    /**
     * @param imageAlpha 背景图片
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:37
     */
    public static BufferedImage insertImageAlpha(ImageAlpha imageAlpha) throws Exception {
        Preconditions.checkArgument(imageAlpha != null, "没有指定背景图片信息");

        byte[] imageAlphaBytes = imageAlpha.getSrcIamge();
        BufferedImage bgBufferedImage;

        if (imageAlphaBytes == null || imageAlphaBytes.length == 0) {
            String imageImageAlphaPath = imageAlpha.getSrcPath();

            if (StringUtils.isBlank(imageImageAlphaPath)) {
                throw new IllegalArgumentException("未指定背景图片字节数组且背景图片文件也未指定！");
            }

            IOUtil.checkAndSetFormatName(imageAlpha, imageImageAlphaPath);

            File file = new File(imageImageAlphaPath);

            if (!file.exists()) {
                throw new IllegalArgumentException(String.format("背景图片[%s]不存在！", imageImageAlphaPath));
            }

            bgBufferedImage = ImageIO.read(new File(imageImageAlphaPath));
        } else {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageAlphaBytes);
            bgBufferedImage = ImageIO.read(byteArrayInputStream);
        }

        //
        BufferedImage newBgBufferedImage = new BufferedImage(bgBufferedImage.getWidth(), bgBufferedImage.getHeight(), imageAlpha.getImageType());
        // 获取画笔
        Graphics2D graphics2D = newBgBufferedImage.createGraphics();
        // 背景图设置透明度
        graphics2D.setComposite(imageAlpha.getComposite());
        // 将newBgBufferedImage对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
        graphics2D.drawImage(bgBufferedImage, imageAlpha.getX(), imageAlpha.getY(), imageAlpha.getObserver());
        graphics2D.dispose();

        log.info("图片成功改变透明度");

        return newBgBufferedImage;
    }
} 