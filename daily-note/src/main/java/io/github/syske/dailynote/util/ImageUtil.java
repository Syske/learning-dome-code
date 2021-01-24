package io.github.syske.dailynote.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

    private BufferedImage image;
    private int imageWidth = 600;  //图片的宽度
    private int imageHeight = 800; //图片的高度

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

    private void createHeader(int headerHeight) throws ParseException {
        HashMap<Character, String> weekDirs = new HashMap<>(7);
        weekDirs.put('日', "Sun");
        weekDirs.put('一', "Mon");
        weekDirs.put('二', "Tue");
        weekDirs.put('三', "Wed");
        weekDirs.put('四', "Thu");
        weekDirs.put('五', "Fri");
        weekDirs.put('六', "Sat");
        Graphics header = image.createGraphics();

        int headerWidth = image.getWidth();
        //设置字体颜色，先设置颜色，再填充内容
        header.setColor(Color.BLACK);
        //设置字体
        int fontSize = 30;
        Font titleFont = new Font("宋体", Font.BOLD, fontSize);
        header.setFont(titleFont);
        Date nowDate = new Date();
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(nowDate);
        System.out.println(element);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String contentFirstLineLeft = weekDirs.get(element.getWeek());
        header.drawString(contentFirstLineLeft, 70, (headerHeight-fontSize*2)/4 + 70);
        String contentFirstLineRight = dateFormat.format(nowDate);
        int contentWidth = contentFirstLineRight.length() * fontSize*2/3;
        header.drawString(contentFirstLineRight, headerWidth - contentWidth - 70, (headerHeight-fontSize*2)/4 + 70);

        String contentSecondLineLeft = "周" + element.getWeek();
        header.drawString(contentSecondLineLeft, 70, (headerHeight) / 2 + 5 + 70);
        String contentSecondLineRight = "农历" + element.getcYear() + element.getcMonth() +
                element.getcDay();
        int i = contentSecondLineRight.length() * fontSize;
        header.drawString(contentSecondLineRight, headerWidth - i - 70, (headerHeight) / 2 + 5 + 70);

        drawBox(header, 50, 50, image.getWidth() - 50, 200);
    }

    private void drawBox(Graphics header, int x, int y, int width, int height) {
        header.drawLine(x, y, width , y);
        header.drawLine(width, y, width , height);
        header.drawLine(x, height, width , height);
        header.drawLine(x, y, x, height);
    }

    private void createFooter(String footerImgUrl) {
        Graphics footer = image.getGraphics();
        BufferedImage bimg = null;
        String content = "-【每日读书札记】-";
        footer.setColor(Color.BLACK);
        //设置字体
        int fontSize = 24;
        Font titleFont = new Font("宋体", Font.PLAIN, fontSize);
        footer.setFont(titleFont);
        footer.drawString(content, (image.getWidth()-content.length()*fontSize)/2, image.getHeight()-120);
        try {
            bimg = javax.imageio.ImageIO.read(new java.io.File(footerImgUrl));
        } catch (Exception e) {
        }

        if (bimg != null) {
            footer.drawImage(bimg, image.getWidth()-120, image.getHeight()-120, 100, 100, null);
            footer.dispose();
        }
    }

    //imgurl插入广告图片路径，imgPath最终图片保存路径
    public void graphicsGeneration(String imgurl, String imgPath) throws ParseException {
        int H_title = 100;     //头部高度
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        //设置区域颜色
        main.setColor(new Color(251, 253, 232));
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************页面头部
        createHeader(200);


        //***********************插入中间广告图
        Graphics2D mainPic = image.createGraphics();
        String mainContent = "生命不是因为走运才变好，而是在改变中变好。改变总是从内部开始，是想法的改变让生命更美好。";
        mainPic.setColor(Color.BLACK);
        int fontSize = 28;
        Font titleFont = new Font("宋体", Font.PLAIN, fontSize);
        mainPic.setFont(titleFont);

        int lineNum = drawString(mainPic, 30, 380, mainContent, (imageWidth-30*2)/ fontSize);

        String authorInfo = "—— [美]约翰·麦克斯韦尔《世界上到处都是有才华的穷人》";
        drawString(mainPic, 30, 30 + 380 + (lineNum + 1) * 30, authorInfo, (imageWidth-30*2)/ fontSize);

        createFooter(imgurl);


        createImage(imgPath);

    }

    private int drawString(Graphics2D mainPic, int x, int y, String mainContent, int lineWordsNum) {
        int length = mainContent.length();
        StringBuilder contentBuilder = new StringBuilder(mainContent);
        int lineNum = 0;
        int startIndex = 0;
        while(contentBuilder.length() > lineWordsNum) {
            int endIndex = startIndex + lineWordsNum;
            mainPic.drawString(mainContent.substring(startIndex, endIndex), x, y + lineNum * 30);
            contentBuilder.delete(startIndex, endIndex);
            startIndex = endIndex;
            lineNum ++;
        }
        mainPic.drawString(mainContent.substring(startIndex, length), x, y + lineNum * 30);
        return lineNum;
    }
}
