package io.github.syske.websigndemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @program: szyb-public-server
 * @description: 图片处理工具类
 * @author: syske
 * @create: 2020-12-28 19:07
 */
public class ImageUtil {
    private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /** 图片添加文字水印
     * @description
     * @param sourceImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param fileExt 图片格式
     * @return void
     */
    public void addWatermark(String sourceImgPath, String tarImgPath,
                             String waterMarkContent,String fileExt, double cos) throws Exception {
        Font font = new Font("宋体", Font.BOLD, 25);//水印字体，大小
        Color markContentColor = Color.red;//水印颜色
        Integer degree = 45;//设置水印文字的旋转角度
        float alpha = 0.5f;//设置水印透明度
        OutputStream outImgStream = null;
        try {
            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //设置水印颜色
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree));//设置水印旋转
            }
            JLabel label = new JLabel(waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(font);
            int width = (int) Math.ceil(metrics.stringWidth(label.getText())/cos);//文字水印的宽
            int rowsNumber = srcImgHeight/width;// 图片的高  除以  文字水印的宽    ——> 打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth/width;//图片的宽 除以 文字水印的宽   ——> 每行打印的列数(以文字水印的宽为间隔)
            //防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }
            for(int j=0;j<rowsNumber;j++){
                for(int i=0;i<columnsNumber;i++){
                    g.drawString(waterMarkContent, i*width + j*width , -i*width + j*width);//画出水印,并设置水印位置
                }
            }
            g.dispose();// 释放资源
            // 输出图片
            outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, fileExt, outImgStream);
        } catch (Exception e) {
            throw new Exception("添加水印失败", e);
        } finally{
            try {
                if(outImgStream != null){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (Exception e) {
                throw new Exception("添加水印失败", e);
            }
        }
    }

    /**
     *
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param buffImg 源文件(BufferedImage)
     * @param waterImg 水印文件(BufferedImage)
     * @param x 距离右下角的X偏移量
     * @param y  距离右下角的Y偏移量
     * @param alpha  透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @param scale  缩放比例，整数，如50表示缩放为0.5
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage overlyingImage(BufferedImage buffImg, BufferedImage waterImg,
                                               int x, int y, float alpha, int scale) {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth = waterImg.getWidth()*scale/100;// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight()*scale/100;// 获取层图的高度
        logger.debug("图片宽度" + waterImgWidth);
        logger.debug("图片高度" + waterImgHeight);
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl)
            throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }

    /**
     * 远程图片转BufferedImage
     * @param destUrl    远程图片地址
     * @return
     */
    public static BufferedImage getBufferedImageDestUrl(String destUrl) throws Exception{
        HttpURLConnection conn = null;
        BufferedImage image = null;
        try {
            URL url = new URL(destUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                image = ImageIO.read(conn.getInputStream());
                return image;
            }
        } catch (Exception e) {
            throw new Exception("获取远程图片失败", e);
        } finally {
            conn.disconnect();
        }
        return image;
    }

    /**
     * 输出图片
     * @param buffImg  BufferedImage对象
     * @param savePath 图像保存路径
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) throws Exception{
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            File outFile = new File(savePath);
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            ImageIO.write(buffImg, savePath.substring(temp), outFile);
            System.out.println("ImageIO write..." + savePath);
        } catch (IOException e) {
            throw new Exception("保存图片失败", e);
        }
    }


    /**
     * 图片base64字符串转BufferedImage
     * @param imageBase64Str 图片base64字符串
     * @return
     * @throws Exception
     */
    public static BufferedImage getInputStreamFormBase64(String imageBase64Str) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        // 上传图片失败
        try {
            byte[] bytes = decoder.decodeBuffer(imageBase64Str);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            return ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            throw new Exception("附件base64图片转换失败");
        }
    }
}
