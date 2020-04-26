package com.lemon.qrcode.util;

import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

@Slf4j
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸    
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度    
    private static final int WIDTH = 60;
    // LOGO高度    
    private static final int HEIGHT = 60;

    /**
     * @param content  内容
     * @param destPath 存储地址
     * @return void
     * @description 生成二维码
     * @author lemon
     * @date 2020-04-25 18:06
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, (String) null, destPath, false);
    }

    /**
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @param destPath      存储地址
     * @return void
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:07
     */
    public static void encode(String content, String imageLogoPath, String destPath) throws Exception {
        QRCodeUtil.encode(content, imageLogoPath, destPath, false);
    }

    /**
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @return void
     * @description 生成二维码
     * @author lemon
     * @date 2020-04-25 18:07
     */
    public static void encode(String content, String destPath, boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, (String) null, destPath, needCompress);
    }

    /**
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @param destPath      存储地址
     * @param needCompress  是否压缩LOGO
     * @return java.lang.String
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:09
     */
    public static String encode(String content, String imageLogoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imageLogoPath, needCompress);
        String filePath = QRCodeUtil.writeFile(image, destPath);
        return filePath;
    }

    /**
     * @param content   内容
     * @param iamgeLogo LOGO
     * @param destPath  存储地址
     * @return void
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:07
     */
    public static void encode(String content, byte[] iamgeLogo, String destPath) throws Exception {
        QRCodeUtil.encode(content, iamgeLogo, destPath, false);
    }

    /**
     * @param content      内容
     * @param iamgeLogo    LOGO
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @return java.lang.String
     * @description
     * @author lemon
     * @date 2020-04-25 20:04
     */
    public static String encode(String content, byte[] iamgeLogo, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, iamgeLogo, needCompress);
        String filePath = QRCodeUtil.writeFile(image, destPath);
        return filePath;
    }

    /**
     * @param output  输出流
     * @param content 内容
     * @return void
     * @description 生成二维码
     * @author lemon
     * @date 2020-04-25 18:05
     */
    public static void encode(OutputStream output, String content) throws Exception {
        QRCodeUtil.encode(output, content, (String) null, false);
    }

    /**
     * @param output        输出流
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @param needCompress  是否压缩LOGO
     * @return void
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:06
     */
    public static void encode(OutputStream output, String content, String imageLogoPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imageLogoPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * @param output       输出流
     * @param content      内容
     * @param imageLogo    LOGO
     * @param needCompress 是否压缩LOGO
     * @return void
     * @description 生成二维码(内嵌LOGO)
     * @author lemon
     * @date 2020-04-25 18:06
     */
    public static void encode(OutputStream output, String content, byte[] imageLogo, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imageLogo, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * @param content 内容
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 18:31
     */
    public static byte[] encodeToBytes(String content) throws Exception {
        return QRCodeUtil.encodeToBytes(content, (String) null, false);
    }

    /**
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 18:32
     */
    public static byte[] encodeToBytes(String content, String imageLogoPath) throws Exception {
        return QRCodeUtil.encodeToBytes(content, imageLogoPath, false);
    }

    /**
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @param needCompress  是否压缩LOGO
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 18:32
     */
    public static byte[] encodeToBytes(String content, String imageLogoPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imageLogoPath, needCompress);

        return QRCodeUtil.toByteArray(image);
    }

    /**
     * @param content   内容
     * @param imageLogo LOGO
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 20:05
     */
    public static byte[] encodeToBytes(String content, byte[] imageLogo) throws Exception {
        return QRCodeUtil.encodeToBytes(content, imageLogo, false);
    }

    /**
     * @param content      内容
     * @param imageLogo    LOGO
     * @param needCompress 是否压缩LOGO
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 20:08
     */
    public static byte[] encodeToBytes(String content, byte[] imageLogo, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imageLogo, needCompress);
        return QRCodeUtil.toByteArray(image);
    }

    /**
     * @param path 二维码图片地址
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 18:02
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
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
        return QRCodeUtil.decode(image);
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
        return QRCodeUtil.decode(image);
    }

    /**
     * @param image
     * @return java.lang.String
     * @description 解析二维码
     * @author lemon
     * @date 2020-04-25 20:34
     */
    public static String decode(BufferedImage image) throws NotFoundException {
        if (image == null) {
            return null;
        }

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = Maps.newHashMap();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
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
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, FORMAT_NAME, out);
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
    private static String writeFile(BufferedImage image, String destPath) throws IOException {
        QRCodeUtil.mkdirs(destPath);
        String filePath = destPath + File.separator + System.currentTimeMillis() + ".jpg";
        log.info("生成二维码保存在{}", filePath);
        ImageIO.write(image, FORMAT_NAME, new File(filePath));
        return filePath;
    }

    /**
     * @param content       内容
     * @param imageLogoPath LOGO地址
     * @param needCompress  是否压缩LOGO
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 18:01
     */
    private static BufferedImage createImage(String content, String imageLogoPath, boolean needCompress) throws Exception {
        BufferedImage bufferedImage = QRCodeUtil.createQRCodeImage(content);

        if (StringUtils.isBlank(imageLogoPath)) {
            return bufferedImage;
        }

        // 插入图片
        QRCodeUtil.insertLogoImage(bufferedImage, imageLogoPath, needCompress);
        return bufferedImage;
    }

    /**
     * @param content      内容
     * @param imageLogo    LOGO
     * @param needCompress 是否压缩LOGO
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:59
     */
    private static BufferedImage createImage(String content, byte[] imageLogo, boolean needCompress) throws Exception {
        BufferedImage bufferedImage = QRCodeUtil.createQRCodeImage(content);

        if (imageLogo == null || imageLogo.length == 0) {
            return bufferedImage;
        }

        // 插入图片
        QRCodeUtil.insertLogoImage(bufferedImage, imageLogo, needCompress);
        return bufferedImage;
    }

    /**
     * @param content
     * @return java.awt.image.BufferedImage
     * @description 创建二维码
     * @author lemon
     * @date 2020-04-25 19:56
     */
    private static BufferedImage createQRCodeImage(String content) throws Exception {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        // 获取二维码宽高
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        // 将二维码放入缓冲流
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 循环将二维码内容定入图片
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        QRCodeUtil.setClip(bufferedImage, 10);

        return bufferedImage;
    }

    /**
     * @param bufferedImage
     * @param radius
     * @return java.awt.image.BufferedImage
     * @description 图片切圆角
     * @author lemon
     * @date 2020-04-25 22:11
     */
    public static BufferedImage setClip(BufferedImage bufferedImage, int radius) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(bufferedImage, 0, 0, null);
        gs.dispose();
        return image;
    }

    /**
     * @param qrcodeImage   二维码图片
     * @param imageLogoPath LOGO图片地址
     * @param needCompress  是否压缩
     * @return void
     * @description 插入LOGO
     * @author lemon
     * @date 2020-04-25 17:55
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, String imageLogoPath, boolean needCompress) throws Exception {
        File file = new File(imageLogoPath);

        if (!file.exists()) {
            log.warn("文件[{}]不存在！", imageLogoPath);
            return;
        }

        Image logoImage = ImageIO.read(new File(imageLogoPath));
        QRCodeUtil.insertLogoImage(qrcodeImage, logoImage, needCompress);
    }

    /**
     * @param qrcodeImage  二维码图片
     * @param imageLogo    LOGO图片字节数组
     * @param needCompress 是否压缩
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:37
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, byte[] imageLogo, boolean needCompress) throws Exception {
        if (imageLogo == null || imageLogo.length == 0) {
            log.warn("文件不存在！");
            return;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageLogo);
        Image logoImage = ImageIO.read(byteArrayInputStream);
        QRCodeUtil.insertLogoImage(qrcodeImage, logoImage, needCompress);
    }

    /**
     * @param qrcodeImage  二维码图片
     * @param logoImage    LOGO图片Image
     * @param needCompress 是否压缩
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 18:39
     */
    private static void insertLogoImage(BufferedImage qrcodeImage, Image logoImage, boolean needCompress) throws Exception {
        if (logoImage == null) {
            return;
        }

        int width = logoImage.getWidth(null);
        int height = logoImage.getHeight(null);

        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }

            if (height > HEIGHT) {
                height = HEIGHT;
            }

            Image image = logoImage.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            // 绘制缩小后的图
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            logoImage = image;
        }

        // 插入LOGO
        Graphics2D graph = qrcodeImage.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(logoImage, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
} 