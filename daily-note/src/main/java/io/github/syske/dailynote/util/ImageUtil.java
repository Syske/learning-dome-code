package io.github.syske.dailynote.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.font.FontDesignMetrics;

public class ImageUtil {

    private BufferedImage image;
    private int imageWidth = 1280;  //图片的宽度
    private int imageHeight = 1980; //图片的高度

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
        int headerWidth = image.getWidth();
        //设置字体颜色，先设置颜色，再填充内容
        header.setColor(Color.BLACK);
        //设置字体
        Font titleFont = FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 80f);
        int fontSize = titleFont.getSize();
        int margin = 120;
        header.setFont(titleFont);
        Date nowDate = new Date();
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(nowDate);
        System.out.println(element);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String contentFirstLineLeft = weekDirs.get(element.getWeek());
        header.drawString(contentFirstLineLeft, margin, (headerHeight-fontSize*2)/4 + margin);
        String contentFirstLineRight = dateFormat.format(nowDate);
        int contentWidth = getWordWidth(titleFont, contentFirstLineRight);
        header.drawString(contentFirstLineRight, headerWidth - contentWidth - margin, (headerHeight-fontSize*2)/4 + margin);

        String contentSecondLineLeft = "周" + element.getWeek();
        header.drawString(contentSecondLineLeft, margin, (headerHeight) / 2 + 5 + margin);
        String contentSecondLineRight = "农历" + element.getcYear() + element.getcMonth() +
                element.getcDay();
        int contentSecondLineRightWidth =  getWordWidth(titleFont, contentSecondLineRight);
        header.drawString(contentSecondLineRight, headerWidth - contentSecondLineRightWidth - margin, (headerHeight) / 2 + 5 + margin);

        //drawBox(header, 50, 50, image.getWidth() - 50, headerHeight);
    }

    /**
     * 获取文字宽度
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

    private void drawBox(Graphics header, int x, int y, int width, int height) {
        header.drawLine(x, y, width , y);
        header.drawLine(width, y, width , height);
        header.drawLine(x, height, width , height);
        header.drawLine(x, y, x, height);
    }

    /**
     * 绘制图片页脚
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
        footer.drawString(footerContent, (image.getWidth()-footerContent.length()*fontSize)/2, image.getHeight()-140);
        try {
            bimg = javax.imageio.ImageIO.read(new java.io.File(footerImgUrl));
        } catch (Exception e) {
        }

        if (bimg != null) {
            footer.drawImage(bimg, image.getWidth()-240, image.getHeight()-240, 200, 200, null);
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
        createHeader(300);

        createrMainContent(380, mainContImgPath, content, authorInfo);


        createFooter(qrCodeImgPath, footerContent);


        createImage(imgSaveFullPath);

    }

    /**
     * 绘制核心内容：主图片及文字内容
     * @param startY
     * @param mainContentImgPath
     * @param content
     * @param authorInfo
     * @throws IOException
     */
    private void createrMainContent(int startY, String mainContentImgPath, String content, String authorInfo) throws IOException {
        //***********************插入中间广告图
        BufferedImage contentImg = javax.imageio.ImageIO.read(new URL(mainContentImgPath));

        Graphics2D mainPic = image.createGraphics();
        // 主内容图片高度
        int contentImHeight = (contentImg.getHeight()* imageWidth / contentImg.getWidth());

        mainPic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mainPic.setColor(Color.BLACK);
        Font titleFont = FontUtil.getFont(FontUtil.PINGFANG_FONT, 56f);
        int fontSize = titleFont.getSize();
        mainPic.setFont(titleFont);

        int margin = 50;
        int lineNum = drawString(mainPic, margin, startY + contentImHeight + 100, content, (imageWidth- margin *2)/ fontSize, fontSize + 10);
        int wordWidth = getWordWidth(titleFont, authorInfo);
        drawString(mainPic, imageWidth - margin - wordWidth, margin + startY + contentImHeight + 100 + (lineNum + 1) * (56+10), authorInfo, (imageWidth- margin *2)/ fontSize, fontSize + 10);
        mainPic.drawImage(contentImg, 0, 380, imageWidth, contentImHeight, null);
        mainPic.dispose();
    }

    /**
     * 自动换行添加文字
     * @param mainPic
     * @param x
     * @param y
     * @param mainContent
     * @param lineWordsNum
     * @param lineHeight
     * @return
     */
    private int drawString(Graphics2D mainPic, int x, int y, String mainContent, int lineWordsNum, int lineHeight) {
        int length = mainContent.length();
        StringBuilder contentBuilder = new StringBuilder(mainContent);
        int lineNum = 0;
        int startIndex = 0;
        while(contentBuilder.length() > lineWordsNum) {
            int endIndex = startIndex + lineWordsNum;
            mainPic.drawString(mainContent.substring(startIndex, endIndex), x, y + lineNum * lineHeight);
            contentBuilder.delete(startIndex, endIndex);
            startIndex = endIndex;
            lineNum ++;
        }
        mainPic.drawString(mainContent.substring(startIndex, length), x, y + lineNum * lineHeight);
        return lineNum;
    }

    public String getImageUrl() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.init();
        String resurt = httpClientUtil.doPost("https://api.ixiaowai.cn/gqapi/gqapi.php?return=json", "");
        JSONObject jsonObject = JSON.parseObject(resurt);
        System.out.println(resurt);
        String imgurl = (String)jsonObject.get("imgurl");
        System.out.println(imgurl);
        return imgurl;
    }
}
