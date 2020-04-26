package com.lemon.qrcode.util;

import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.lemon.qrcode.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author lemon
 * @description https://github.com/liuyueyi/quick-media
 * @date 2020-04-26 10:41
 */
@Slf4j
public class QRCodeEntityUtil {
    private static final String DEFAULT_CHARSET = "utf-8";

    private static final String DEFAULT_FORMAT_NAME = "JPG";

    /**
     * @param qrCodeContainer
     * @return java.lang.String
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:09
     */
    public static Object encode(QRCodeContainer qrCodeContainer) throws Exception {
        BufferedImage image = QRCodeEntityUtil.createImage(qrCodeContainer);

        if (StringUtils.isNotBlank(qrCodeContainer.getDestPath())) {
            String filePath = QRCodeEntityUtil.writeFile(image, qrCodeContainer.getFormatName(), qrCodeContainer.getDestPath());
            return filePath;
        }

        if (qrCodeContainer.getOutput() != null) {
            String formatName = qrCodeContainer.getFormatName();

            if (StringUtils.isBlank(formatName)) {
                formatName = DEFAULT_FORMAT_NAME;
            }

            ImageIO.write(image, formatName, qrCodeContainer.getOutput());
            return null;
        }

        return QRCodeEntityUtil.toByteArray(image);
    }

    /**
     * @param qrCodeContainer
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 18:31
     */
    public static byte[] encodeToBytes(QRCodeContainer qrCodeContainer) throws Exception {
        BufferedImage image = QRCodeEntityUtil.createImage(qrCodeContainer);

        return QRCodeEntityUtil.toByteArray(image);
    }

    /**
     * @param path 二维码图片地址
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:02
     */
    public static String decode(String path) throws Exception {
        return QRCodeEntityUtil.decode(new File(path));
    }

    /**
     * @param qrcodes 二维码图片
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:02
     */
    public static String decode(byte[] qrcodes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrcodes);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        return QRCodeEntityUtil.decode(image);
    }

    /**
     * @param file 二维码图片
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:03
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        return QRCodeEntityUtil.decode(image);
    }

    /**
     * @param image
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 20:34
     */
    public static String decode(BufferedImage image) throws NotFoundException {
        return QRCodeEntityUtil.decode(image, DEFAULT_CHARSET);
    }

    /**
     * @param image
     * @param charset
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 20:34
     */
    public static String decode(BufferedImage image, String charset) throws NotFoundException {
        if (image == null) {
            return null;
        }

        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = Maps.newHashMap();
        hints.put(DecodeHintType.CHARACTER_SET, charset);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultValue = result.getText();
        return resultValue;
    }

    /**
     * @param image 二维码
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 20:07
     */
    public static byte[] toByteArray(BufferedImage image) {
        return QRCodeEntityUtil.toByteArray(image, DEFAULT_FORMAT_NAME);
    }

    /**
     * @param image      二维码
     * @param formatName 二维码格式
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 20:07
     */
    public static byte[] toByteArray(BufferedImage image, String formatName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (StringUtils.isBlank(formatName)) {
            formatName = DEFAULT_FORMAT_NAME;
        }

        try {
            ImageIO.write(image, formatName, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * @param destPath 存放目录
     * @return void
     * @description 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @author lemon
     * @date 2020-04-25 18:10
     */
    private static void mkdirs(String destPath) {
        File file = new File(destPath);

        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * @param image
     * @param destPath
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 20:02
     */
    private static String writeFile(BufferedImage image, String formatName, String destPath) throws IOException {
        QRCodeEntityUtil.mkdirs(destPath);
        String filePath = destPath + File.separator + System.currentTimeMillis() + ".jpg";
        log.info("生成二维码保存在{}", filePath);
        ImageIO.write(image, formatName, new File(filePath));
        return filePath;
    }

    /**
     * @param qrCodeContainer
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:59
     */
    private static BufferedImage createImage(QRCodeContainer qrCodeContainer) throws Exception {
        BufferedImage bufferedImage = QRCodeEntityUtil.createQRCodeImage(qrCodeContainer);
        // 插入图片
        QRCodeEntityUtil.insertLogoImage(bufferedImage, qrCodeContainer);

        // 插入水印
        if (StringUtils.isNotBlank(qrCodeContainer.getWaterMark().getContent())) {
            QRCodeEntityUtil.insertWaterMark(bufferedImage, qrCodeContainer);
        }

        return bufferedImage;
    }

    /**
     * @param qrCodeContainer
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:56
     */
    private static BufferedImage createQRCodeImage(QRCodeContainer qrCodeContainer) throws Exception {
        QRCode qrCode = qrCodeContainer.getQrCode();
        // 生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode.getContent(), qrCode.getBarcodeFormat(),
                qrCode.getWidth(), qrCode.getHeight(), qrCode.getHints());
        // 获取二维码宽高
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        // 将二维码放入缓冲流
        BufferedImage bufferedImage = new BufferedImage(width, height, qrCode.getImageType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 循环将二维码内容定入图片
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return bufferedImage;
    }

    /**
     * @param qrcodeImage     二维码图片
     * @param qrCodeContainer
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:37
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, QRCodeContainer qrCodeContainer) throws Exception {
        byte[] imageLogo = qrCodeContainer.getLogo().getIamge();
        Image logoImage;

        if (imageLogo == null || imageLogo.length == 0) {
            String imageLogoPath = qrCodeContainer.getLogo().getPath();

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

//        logoImage = cornerradius((BufferedImage) logoImage, qrCodeContainer.getLogoRadius());
        QRCodeEntityUtil.insertLogoImage(qrcodeImage, logoImage, qrCodeContainer);
    }

    /**
     * @param qrcodeImage     二维码图片
     * @param logoImage       LOGO图片Image
     * @param qrCodeContainer
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:39
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, Image logoImage, QRCodeContainer qrCodeContainer) {
        if (logoImage == null) {
            return;
        }

        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);
        Logo logo = qrCodeContainer.getLogo();

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
            graphics.drawImage(image, 0, 0, logo.getObserver());
            // 释放资源
            graphics.dispose();

            logoImage = image;
        }

        int qrCodeWidth = qrCodeContainer.getQrCode().getWidth();
        int qrCodeHeight = qrCodeContainer.getQrCode().getHeight();
        int x = (qrCodeWidth - width) / 2;
        int y = (qrCodeHeight - height) / 2;
        // 插入LOGO
        Graphics2D graphics2D = qrcodeImage.createGraphics();
        graphics2D.drawImage(logoImage, x, y, width, height, logo.getObserver());
        graphics2D.setColor(logo.getColor());
        // 在图形和图像中实现混合和透明效果
        graphics2D.setComposite(qrCodeContainer.getLogo().getComposite());
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, qrCodeContainer.getLogoShape().getArcw(),
                qrCodeContainer.getLogoShape().getArch());
        graphics2D.setStroke(new BasicStroke(qrCodeContainer.getLogoStroke().getWidth()));
        graphics2D.draw(shape);
        graphics2D.dispose();

        log.info("成功插入Logo, 二维码:宽度{}、高度{}, 插入位置:x {}, y {}", qrCodeWidth, qrCodeHeight, x, y);
    }


    /**
     * @param bufferedImage
     * @param logoRadius
     * @return java.awt.image.BufferedImage
     * @description 图片切圆角
     * @author lemon
     * @date 2020-04-25 22:11
     */
    public static BufferedImage cornerradius(BufferedImage bufferedImage, LogoRadius logoRadius) {
        if (logoRadius == null) {
            return bufferedImage;
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, logoRadius.getImageType());
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(logoRadius.getHintKey(), logoRadius.getHintValue());
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, logoRadius.getArcw(), logoRadius.getArch()));
        gs.drawImage(bufferedImage, 0, 0, logoRadius.getObserver());
        gs.dispose();

        log.info("成功图片切圆角");
        return image;
    }

    /**
     * @param qrcodeImage
     * @param qrCodeContainer
     * @return void
     * @description 插入水印
     * @author lemon
     * @date 2020-04-26 09:42
     */
    private static void insertWaterMark(BufferedImage qrcodeImage, QRCodeContainer qrCodeContainer) {
        if (StringUtils.isBlank(qrCodeContainer.getWaterMark().getContent())) {
            return;
        }

        WaterMark waterMark = qrCodeContainer.getWaterMark();
        int qrCodeWidth = qrCodeContainer.getQrCode().getWidth();
        int qrCodeHeight = qrCodeContainer.getQrCode().getHeight();

        // 获取画笔，插入水印
        Graphics2D graphics2D = qrcodeImage.createGraphics();
        // 设置透明度
        graphics2D.setComposite(waterMark.getComposite());
        // 设置背景颜色
        graphics2D.setBackground(waterMark.getBackground());
        // 设置“抗锯齿”的属性
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置字体类型和大小
        graphics2D.setFont(waterMark.getFont());
        // 设置字体颜色
        graphics2D.setColor(waterMark.getColor());

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

        log.info("成功写入水印内容{}, 二维码:宽度{}、高度{}, 插入位置:x {}, y {}", waterMark.getContent(), qrCodeWidth, qrCodeHeight, x, y);
    }
} 