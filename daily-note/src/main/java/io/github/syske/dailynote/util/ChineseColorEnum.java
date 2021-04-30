package io.github.syske.dailynote.util;

import java.awt.*;

/**
 *
 * 颜色根据中国色网站整理，网站地址：http://zhongguose.com/
 * @program: daily-note
 * @description: 中国色
 * @author: syske
 * @create: 2021-04-30 08:06
 */
public enum ChineseColorEnum {


    /**
     * 玉红色
     */
    YU_HONG(new Color(192, 72, 81), "玉红", "玉红色"),
    /**
     * 艳红色
     */
    YAN_HONG(new Color(237, 90, 101), "艳红", "艳红色"),

    /**
     *  鹅掌黄
     */
    E_ZHANG_HUANG(new Color(251, 185, 41), "鹅掌黄", "鹅掌黄色");

    /**
     * 颜色
     */
    private Color color;
    /**
     * 颜色名称
      */
    private String colorName;
    /**
     * 颜色描述
      */
    private String colorDescription;

    ChineseColorEnum(Color color, String colorName, String colorDescription) {
        this.color = color;
        this.colorName = colorName;
        this.colorDescription = colorDescription;
    }

    public Color getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorDescription() {
        return colorDescription;
    }


    /**
     * 根据RGB值判断 深色与浅色
     * @param color 颜色
     * @return
     */
    public static boolean isDark(Color color){
        //浅色
        if(color.getRed()*0.299 + color.getGreen()*0.578 + color.getBlue()*0.114 >= 192){
            return false;
        }else{
            //深色
            return true;
        }
    }
}
