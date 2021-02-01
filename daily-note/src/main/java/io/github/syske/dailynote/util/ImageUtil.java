package io.github.syske.dailynote.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.font.FontDesignMetrics;

public class ImageUtil {
    private final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private BufferedImage image;

    private int imageWidth = 1215;  //图片的宽度
    private int imageHeight = 2160; //图片的高度
    private int footerHeight = 280; //图片的高度

    //生成图片文件
    @SuppressWarnings("restriction")
    public void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if (image != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(image);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 绘制图片页眉
     *
     * @param headerHeight
     * @throws ParseException
     */
    private void createHeader(int headerHeight) throws ParseException {
        HashMap<Character, String> weekDirs = new HashMap<>(7);
        weekDirs.put('日', "Sun");
        weekDirs.put('一', "Mon");
        weekDirs.put('二', "Tue");
        weekDirs.put('三', "Wed");
        weekDirs.put('四', "Thu");
        weekDirs.put('五', "Fri");
        weekDirs.put('六', "Sat");
        Graphics2D header = image.createGraphics();
        // 抗锯齿
        header.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //设置字体颜色，先设置颜色，再填充内容
        header.setColor(Color.BLACK);
        //设置字体
        Font titleFontBig = FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 260f);
        Font titleFont = FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 80f);
        Font titleFontLitter = FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 60f);
        Font titleFontSmall = FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 50f);
        int margin = 120;
        header.setFont(titleFontBig);
//        Date today = new Date();
        SimpleDateFormat dateFormatYmd = new SimpleDateFormat("yyyy-MM-dd");
        Date today = dateFormatYmd.parse("2021-02-03");
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(today);
        logger.debug("农历：" + element.toString());
        // 日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String contentFirstLineRight = dateFormat.format(today);
        int bigDateY = headerHeight / 2 + getFontAscent(titleFontBig) - titleFontBig.getSize() / 2;
        logger.debug("日期y坐标：" + bigDateY);
        header.drawString(contentFirstLineRight, margin, bigDateY);

        // 中文年月日
        header.setColor(Color.black);
        header.setFont(titleFontLitter);
        StringBuilder contentSecondLineRight = new StringBuilder(element.getlMonthChinese() + element.getlDayChinese());
        String solarTerms = element.getLunarTerms();
        if(!StringUtils.isEmpty(solarTerms)) {
            contentSecondLineRight.append(" | 今日" + solarTerms);
        }
        int contentSecondX = getWordWidth(titleFontBig, contentFirstLineRight) + margin + 20;
//        header.drawLine(0, bigDateY - getFontAscent(titleFontBig), imageWidth, bigDateY - getFontAscent(titleFontBig));
//        header.drawLine(0, bigDateY - getFontAscent(titleFontBig) + titleFontBig.getSize(), imageWidth, bigDateY - getFontAscent(titleFontBig) + titleFontBig.getSize());
        int contentSecondY = bigDateY - getFontAscent(titleFontBig) + getFontAscent(titleFont) - getFontDescent(titleFont);
        header.drawString(contentSecondLineRight.toString(), contentSecondX, contentSecondY);

        header.setColor(Color.GRAY);
        header.setFont(titleFontLitter);
        String contentSecondLine = element.getcYear() + "[" + element.getcAnimal() + "]年 " + element.getcMonth() + "月 "
                + element.getcDay() + "日";
        header.drawString(contentSecondLine, contentSecondX, bigDateY - getFontAscent(titleFont));

        // 日期提示：日期宜忌
        header.setFont(titleFontSmall);
        header.setColor(Color.LIGHT_GRAY);
        String dateTips = element.getSgz5();
        // 倒计时
        int countDownDays = DateUtil.getCountDownDays("2021-02-12", today);
        if (StringUtils.isEmpty(dateTips) && countDownDays > 0) {
            dateTips = "距离春节还有" + countDownDays + "天";
        } else {
            dateTips = "今天是春节";
        }
        header.drawString(dateTips, contentSecondX, bigDateY);


    }

    /**
     * 绘制图片页脚
     *
     * @param footerImgUrl
     * @param footerContent
     */
    private void createFooter(String footerImgUrl, String footerContent) {
        Graphics2D footer = image.createGraphics();
        BufferedImage bimg = null;
        footer.setColor(Color.BLACK);
        footer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //设置字体
        int fontSize = 60;
        Font titleFont = FontUtil.getFont(FontUtil.PINGFANG_FONT, 60.0f);

        footer.setFont(titleFont);
        drawDashedLine(footer, imageHeight - footerHeight, 0, imageWidth, 20);
        footer.drawString(footerContent, (image.getWidth() - footerContent.length() * fontSize) / 2, image.getHeight() - 140);
        try {
            bimg = javax.imageio.ImageIO.read(new URL(footerImgUrl));
        } catch (Exception e) {
        }

        if (bimg != null) {
            footer.drawImage(bimg, image.getWidth() - footerHeight + 40, image.getHeight() - footerHeight + 40, 200, 200, null);
            footer.dispose();
        }
    }

    /**
     * 生成读书笔记卡片
     *
     * @param qrCodeImgPath
     * @param imgSaveFullPath
     * @param mainContImgPath
     * @param content
     * @param authorInfo
     * @throws Exception
     */
    public void createReadingNoteCard(String qrCodeImgPath, String imgSaveFullPath, String mainContImgPath,
                                      String content, String authorInfo, String footerContent) throws Exception {
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        //设置区域颜色
        main.setColor(new Color(248, 253, 246));
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************页面头部
        int headerHeight = imageHeight / 5;
        logger.debug("头部高度：" + headerHeight);
        createHeader(headerHeight);
        int margin = 50;
        Font contentFont = FontUtil.getFont(FontUtil.PINGFANG_FONT, 56f);
        createrMainContent(headerHeight, mainContImgPath, content, authorInfo, margin, contentFont);


        createFooter(qrCodeImgPath, footerContent);


        createImage(imgSaveFullPath);

    }

    /**
     * 绘制核心内容：主图片及文字内容
     *
     * @param startY
     * @param mainContentImgPath
     * @param content
     * @param authorInfo
     * @throws IOException
     */
    private void createrMainContent(int startY, String mainContentImgPath,
                                    String content, String authorInfo, int margin, Font font) throws IOException {
        //***********************插入中间广告图
        BufferedImage contentImg = javax.imageio.ImageIO.read(new URL(mainContentImgPath));

        Graphics2D mainPic = image.createGraphics();
        // 主内容图片高度
        int contentImgHeight = (contentImg.getHeight() * imageWidth / contentImg.getWidth());

        mainPic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mainPic.setColor(Color.BLACK);
        int fontSize = font.getSize();
        mainPic.setFont(font);

        logger.debug("图片高度：" + contentImgHeight);
        mainPic.drawImage(contentImg, 0, startY, imageWidth, contentImgHeight, null);

        int mainContentHeight = imageHeight - startY - footerHeight - contentImgHeight;
        logger.debug("笔记核心内容高度：" + mainContentHeight);
        int lineWordsNum = (imageWidth - margin * 2) / fontSize;
        int lineHeight = fontSize + 10;
        int contentHeight = getContentHeight(lineHeight, content, lineWordsNum);
        int authorInfoHeight = getContentHeight(lineHeight, authorInfo, lineWordsNum);
        int contentStartY = startY + contentImgHeight + (mainContentHeight - contentHeight - authorInfoHeight) / 2 + getFontAscent(font) - contentHeight / 4;
        logger.debug("行数：" + lineWordsNum);
        logger.debug("每行字数：" + lineWordsNum);
        logger.debug("笔记行高：" + lineHeight);
        logger.debug("笔记内容高度：" + contentHeight);
        logger.debug("笔记内容y坐标：" + contentStartY);
        logger.debug("作品信息高度：" + authorInfoHeight);
        drawString(mainPic, margin, contentStartY, content, lineWordsNum, lineHeight);
        int wordWidth = getWordWidth(font, authorInfo);

        int authorInfoY = contentStartY + margin + contentHeight + authorInfoHeight / 2;
        logger.debug("作品信息y坐标：" + authorInfoY);
        drawString(mainPic, imageWidth - margin - wordWidth, authorInfoY, authorInfo, lineWordsNum, lineHeight);
        mainPic.dispose();
    }

    /**
     * 获取随机图片，返回图片地址
     *
     * @return
     * @throws Exception
     */
    public String getImageUrl() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.init();
        String resurt = httpClientUtil.doPost("https://api.ixiaowai.cn/gqapi/gqapi.php?return=json", "");
        JSONObject jsonObject = JSON.parseObject(resurt);
        System.out.println(resurt);
        String imgurl = (String) jsonObject.get("imgurl");
        System.out.println(imgurl);
        return imgurl;
    }

    /**
     * 返回文字段落高度
     *
     * @param lineHeight
     * @param content
     * @param lineWordsNum
     * @return
     */
    private int getContentHeight(int lineHeight, String content, int lineWordsNum) {
        int length = content.length();
        if (length <= lineWordsNum)
            return lineHeight;
        int y = length % lineWordsNum;
        if (y == 0)
            return length / lineWordsNum;
        return (((length - y) / lineWordsNum) + 1) * lineHeight;
    }

    /**
     * 自动换行添加文字
     *
     * @param graphics2D   画板
     * @param x            x坐标
     * @param y            y坐标
     * @param content      内容
     * @param lineWordsNum 每行字数
     * @param lineHeight   行高
     * @return
     */
    private int drawString(Graphics2D graphics2D, int x, int y, String content, int lineWordsNum, int lineHeight) {
        int length = content.length();
        StringBuilder contentBuilder = new StringBuilder(content);
        int lineNum = 0;
        int startIndex = 0;
        while (contentBuilder.length() > lineWordsNum) {
            int endIndex = startIndex + lineWordsNum;
            graphics2D.drawString(contentBuilder.substring(0, lineWordsNum), x, y + lineNum * lineHeight);
            contentBuilder.delete(0, lineWordsNum);
            startIndex = endIndex;
            lineNum++;
        }
        graphics2D.drawString(content.substring(startIndex, length), x, y + lineNum * lineHeight);
        return lineNum;
    }

    /**
     * 绘制水平虚线
     *
     * @param graphics2D 画布
     * @param y          y轴坐标
     * @param x          起始x坐标
     * @param endX       终止x坐标
     * @param size       虚线大小（小短线的长度）
     */
    private void drawDashedLine(Graphics2D graphics2D, int y, int x, int endX, int size) {
        int times = Math.abs(x - endX) / (size * 2);
        int count = 0;
        while (count <= times) {
            int x1 = x + count * size * 2;
            graphics2D.drawLine(x1, y, x1 + size, y);
            count++;
        }
    }

    /**
     * 获取文字宽度
     *
     * @param font
     * @param content
     * @return
     */
    private static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    /**
     * 获取字体高度
     *
     * @param font
     * @return
     */
    private static int getWordHeight(Font font) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getHeight();
    }

    /**
     * 获取字体基准线baseline以上高度
     *
     * @param font
     * @return
     */
    private static int getFontAscent(Font font) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getAscent();
    }


    /**
     * 获取字体基准线baseline以下高度
     *
     * @param font
     * @return
     */
    private static int getFontDescent(Font font) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getDescent();
    }

    /**
     * 绘制矩形边框
     *
     * @param graphics 画布
     * @param x        x坐标
     * @param y        y坐标
     * @param width    宽度
     * @param height   高度
     */
    private void drawBox(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawLine(x, y, width, y);
        graphics.drawLine(width, y, width, height);
        graphics.drawLine(x, height, width, height);
        graphics.drawLine(x, y, x, height);
    }


}
