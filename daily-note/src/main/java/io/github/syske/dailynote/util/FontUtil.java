package io.github.syske.dailynote.util;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: daily-note
 * @description:
 * @author: syske
 * @create: 2021-01-25 18:27
 * 字体工具类
 * Created by 刘彦民 on 2018/5/5.
 */
public class FontUtil {
    /**
     * 默认字体
     */
    public static final int DEFAULT_FONT = 1;
    /**
     * PingFangSC字体
     */
    public static final int PINGFANG_FONT = 2;
    /**
     * PingFangSCBold字体
     */
    public static final int PINGFANG_BOLD_FONT = 3;
    /**
     * 方正兰亭特黑GBK
     */
    public static final int FZLTTH_GBK_FONT = 4;

    /**
     * 李旭科行书
     */
    public static final int LI_XU_KE_FONT = 5;

    /**
     * 根据字体类型获取字体
     *
     * @param type
     * @param size
     * @return
     */
    public static Font getFont(int type, float size) {
        // 字体路径
        InputStream fontInputStream = null;
        switch (type) {
            case PINGFANG_FONT:
                fontInputStream = getFontInputStream("font/PingFangSC.ttf");
                break;
            case PINGFANG_BOLD_FONT:
                fontInputStream = getFontInputStream("font/PingFangBold.ttf");
                break;
            case FZLTTH_GBK_FONT:
                fontInputStream = getFontInputStream("font/fzltthjwgb10.ttf");
                break;
            case LI_XU_KE_FONT:
                fontInputStream = getFontInputStream("font/lixukexingshu.ttf");
                break;
            default:
                fontInputStream = getFontInputStream("font/simhei.ttf");
        }

        try {
            Font sPfBoldFont = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
            sPfBoldFont = sPfBoldFont.deriveFont(size);
            return sPfBoldFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fontInputStream != null) {
                    fontInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static InputStream getFontInputStream(String name) {
        return FontUtil.class.getClassLoader().getResourceAsStream(name);
    }
}