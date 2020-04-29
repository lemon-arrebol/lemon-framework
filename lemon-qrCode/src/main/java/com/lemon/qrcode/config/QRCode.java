package com.lemon.qrcode.config;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lemon.qrcode.strategy.DefaultImageRenderStrategy;
import com.lemon.qrcode.strategy.ImageRenderStrategy;
import com.lemon.qrcode.util.ConstantUtil;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class QRCode extends OutputConfig {
    /**
     * 二维码内容
     */
    private String content;

    /**
     * 二维码宽度
     */
    private int width = 300;

    /**
     * 二维码高度
     */
    private int height = 300;

    /**
     * 二维码边距，设置二维码空白区域大小
     */
    private int margin = 1;

    /**
     * 二维码一共有40个尺寸。官方叫版本Version。Version 1是21 x 21的矩阵，Version 2是 25 x 25的矩阵，Version 3是29的尺寸，
     * 每增加一个version，就会增加4的尺寸，公式是：(V-1)*4 + 21（V是版本号） 最高Version 40，(40-1)*4+21 = 177，所以最高是177 x 177 的正方形。
     * 而三个角的探测点的长度相对来讲是固定的。
     */
    private int qrVersionNumber = 6;

    /**
     * 二维码容错率，设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
     */
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.H;

    private int imageType = BufferedImage.TYPE_INT_RGB;

    private Map<EncodeHintType, Object> hints = Maps.newHashMap();

    private BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

    private ImageRadius imageRadius;

    private ImageRenderStrategy imageRenderStrategy = new DefaultImageRenderStrategy();

    /**
     * 二维码定位图形显示颜色
     */
    private Supplier<Color> onColor = () -> Color.BLACK;

    /**
     * 二维码定位图形不显示颜色
     */
    private Supplier<Color> offColor = () -> Color.WHITE;

    /**
     * 二维码左上角探测图形颜色
     */
    private Supplier<Color> leftTopFinderOnColor = () -> Color.GRAY;

    /**
     * 二维码左下角探测图形颜色
     */
    private Supplier<Color> leftDownFinderOnColor = () -> Color.RED;

    /**
     * 二维码右上角探测图形颜色
     */
    private Supplier<Color> rightTopFinderOnColor = () -> Color.BLUE;

    public QRCode() {
        // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, ConstantUtil.DEFAULT_CHARSET);
        hints.put(EncodeHintType.MARGIN, margin);
        hints.put(EncodeHintType.QR_VERSION, qrVersionNumber);
    }
}
