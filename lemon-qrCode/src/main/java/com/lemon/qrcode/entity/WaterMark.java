package com.lemon.qrcode.entity;

import lombok.Data;

import java.awt.*;

/**
 * @author lemon
 * @version 1.0
 * @description: 水印
 * @date Create by lemon on 2020-04-26 09:10
 */
@Data
public class WaterMark {
    private int x;

    private int y;

    private String formatName = "png";

    /**
     * 水印内容
     */
    private String content;
    private Font font = new Font("微软雅黑", Font.PLAIN, 20);
    private Color color = new Color(255, 33, 11);
    private Color background = Color.YELLOW;
    private float alpha = 0.4f;
    // alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
    private Composite composite;
    private RenderingHints.Key hintKey = RenderingHints.KEY_ANTIALIASING;

    private Object hintValue = RenderingHints.VALUE_ANTIALIAS_ON;

    public WaterMark() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
    }
}
