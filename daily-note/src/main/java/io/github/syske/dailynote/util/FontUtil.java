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
        String path = "";
        switch (type) {
            case DEFAULT_FONT:
                path = "D:/tmp/img/test/font/simhei.ttf";
                break;
            case PINGFANG_FONT:
                path = "D:/tmp/img/test/font/PingFangSC.ttf";
                break;
            case PINGFANG_BOLD_FONT:
                path = "D:/tmp/img/test/font/PingFangBold.ttf";
                break;
            case FZLTTH_GBK_FONT:
                path = "D:/tmp/img/test/font/fzltthjwgb10.ttf";
                break;
            case LI_XU_KE_FONT:
                path = Font.class.getResource("/") + "/font/lixukexingshu.ttf";
                break;
            default:
                path = "D:/tmp/img/test/font/simhei.ttf";
        }

        File file = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Font sPfBoldFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            sPfBoldFont = sPfBoldFont.deriveFont(size);
            return sPfBoldFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}