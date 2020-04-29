package com.lemon.qrcode.util;

import com.google.common.base.Preconditions;
import com.lemon.qrcode.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lemon
 * @description https://github.com/liuyueyi/quick-media
 * @date 2020-04-26 10:41
 */
@Slf4j
public class IOUtil {
    /**
     * @param baseConfig
     * @param file
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-27 09:58
     */
    public static void checkAndSetFormatName(BaseConfig baseConfig, File file) {
        IOUtil.checkAndSetFormatName(baseConfig, file.getPath());
    }

    /**
     * @param baseConfig
     * @param file
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-27 09:56
     */
    public static void checkAndSetFormatName(BaseConfig baseConfig, String file) {
        Preconditions.checkArgument(file.lastIndexOf(".") != -1, String.format("%s 不是一个合法的图片", file));
        baseConfig.setFormatName(file.substring(file.lastIndexOf(".") + 1));
    }

    /**
     * @param image 二维码
     * @return byte[]
     * @description
     * @author lemon
     * @date 2020-04-25 20:07
     */
    public static byte[] toByteArray(BufferedImage image) {
        return IOUtil.toByteArray(image, ConstantUtil.DEFAULT_FORMAT_NAME);
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
            formatName = ConstantUtil.DEFAULT_FORMAT_NAME;
        }

        try {
            ImageIO.write(image, formatName, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * @param image
     * @param output
     * @return void
     * @description
     * @author houjuntao
     * @date 2020-04-29 17:08
     */
    public static void write(BufferedImage image, OutputStream output) throws IOException {
        IOUtil.write(image, ConstantUtil.DEFAULT_FORMAT_NAME, output);
    }

    /**
     * @param image
     * @param formatName
     * @param output
     * @return void
     * @description
     * @author houjuntao
     * @date 2020-04-29 17:08
     */
    public static void write(BufferedImage image, String formatName, OutputStream output) throws IOException {
        if (StringUtils.isBlank(formatName)) {
            formatName = ConstantUtil.DEFAULT_FORMAT_NAME;
        }

        ImageIO.write(image, formatName, output);
    }

    /**
     * @param image
     * @param destPath
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 20:02
     */
    public static String writeFile(BufferedImage image, String formatName, String destPath) throws IOException {
        IOUtil.mkdirs(destPath);
        String filePath = String.format("%s%s%s.%s", destPath, File.separator, System.currentTimeMillis(), formatName);
        ImageIO.write(image, formatName, new File(filePath));
        log.info("生成文件保存在{}", filePath);
        return filePath;
    }

    /**
     * @param image
     * @param destFile
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-25 20:02
     */
    public static String write(BufferedImage image, String formatName, String destFile) throws IOException {
        File file = new File(destFile);
        IOUtil.mkdirs(file.getParentFile());
        ImageIO.write(image, formatName, file);
        log.info("生成文件保存在{}", destFile);
        return destFile;
    }

    /**
     * @param destPath 存放目录
     * @return void
     * @description 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @author lemon
     * @date 2020-04-25 18:10
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);

        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        IOUtil.mkdirs(file);
    }

    /**
     * @param destDir 存放目录
     * @return void
     * @description 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @author lemon
     * @date 2020-04-27 08:35
     */
    public static void mkdirs(File destDir) {
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!destDir.exists() && !destDir.isDirectory()) {
            destDir.mkdirs();
        }
    }
} 