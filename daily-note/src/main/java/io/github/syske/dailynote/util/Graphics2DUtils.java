package io.github.syske.dailynote.util;

import org.thymeleaf.util.StringUtils;

import java.awt.*;
import java.util.ArrayList;

public final class Graphics2DUtils {
    /**
     * 向画布上写文字
     *
     * @param g       Graphics2D对象
     * @param color   颜色
     * @param font    字体
     * @param content 内容
     * @param x       坐标x
     * @param y       坐标y
     */
    public static void drawString(Graphics2D g, Color color, Font font, String content, float x, float y) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(content, x, y);
    }

    /**
     * 向画布上写多行文字文字，自动居中
     *
     * @param g           Graphics2D对象
     * @param color       颜色
     * @param font        字体
     * @param content     内容
     * @param x           坐标X
     * @param y           坐标y
     * @param width       画布宽度
     * @param lineWordNum 每行字数
     * @param linePadding 行间距
     * @param center      是否居中
     */
    public static void drawString(Graphics2D g, Color color, Font font, String content, float x, float y, int width, int lineWordNum, int linePadding, boolean center) {
        int num = content.length();

        ArrayList<String> contents = new ArrayList<>();
        if (num <= lineWordNum) {

            contents.add(content);
        } else {
            for (int i = 0; i < num; i += lineWordNum) {
                contents.add(StringUtils.substring(content, i, i + lineWordNum));
            }

        }
        for (int i = 0; i < contents.size(); i++) {
            String s = contents.get(i);
            if (i != 0) {
                y += linePadding + font.getSize();
            }
            if (center) {
                drawCenterString(g, color, font, s, width, y);
            } else {
                drawString(g, color, font, s, x, y);
            }
        }
    }

    /**
     * 向画布上写多行文字文字，自动居中
     *
     * @param g           Graphics2D对象
     * @param color       颜色
     * @param font        字体
     * @param content     内容
     * @param y           坐标y
     * @param width       画布宽度
     * @param lineWordNum 每行字数
     * @param linePadding 行间距
     */
    public static void drawCenterString(Graphics2D g, Color color, Font font, String content, float y, int width, int lineWordNum, int linePadding) {
        drawString(g, color, font, content, 0, y, width, lineWordNum, linePadding, true);
    }

    /**
     * 向画布上写文字，自动居中
     *
     * @param g       Graphics2D对象
     * @param color   颜色
     * @param font    字体
     * @param content 内容
     * @param width   画布宽度
     * @param y       坐标y
     */
    public static void drawCenterString(Graphics2D g, Color color, Font font, String content, int width, float y) {
        int textWidth = getStringWidth(g, font, content);

        drawString(g, color, font, content, (width - textWidth) / 2, y);
    }

    /**
     * 获取字符串内容的宽度
     *
     * @param g       Graphics2D对象
     * @param font    字体
     * @param content 内容
     * @return
     */
    public static int getStringWidth(Graphics2D g, Font font, String content) {
        FontMetrics fm = g.getFontMetrics(font);
        //获取字符串宽度
        return fm.stringWidth(content);
    }
}