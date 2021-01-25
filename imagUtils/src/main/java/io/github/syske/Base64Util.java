package io.github.syske;

/**
 * @program: imagUtils
 * @description: 图片转base64
 * @author: syske
 * @create: 2020-02-18 13:51
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
    private transient static Logger log = LoggerFactory.getLogger(Base64Util.class);
    /**
     * base64字符串转byte
     * @param imageBase64Str
     * @return
     */
    public static byte[] base64topng(String imageBase64Str) {
        byte[] b1 = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            if (imageBase64Str.indexOf("data:image/jpeg;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll("data:image/jpeg;base64,", ""));
            } else if (imageBase64Str.indexOf("data:image/png;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll(
                        "data:image/png;base64,", ""));
            } else if (imageBase64Str.indexOf("data:image/jpg;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll(
                        "data:image/jpg;base64,", ""));
            } else {
                b1 = decoder.decodeBuffer(imageBase64Str);
            }
            for (int i = 0; i < b1.length; ++i) {
                if (b1[i] < 0) {// 调整异常数据
                    b1[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b1;
    }

    /**
     * 保存图片
     * @param realPath
     * @param imageStr base64字符串
     * @return imgName
     */
    public static String filePath(String realPath, String imageStr) {
        String imgPath = "";
        String imgName = "";
        try {
            byte[] imageBytes = base64topng(imageStr);
            File f1 = new File(realPath);
            if (!f1.exists()) {
                f1.mkdir();
            }
            imgName = UUID.randomUUID().toString() + ".jpg";
            imgPath = realPath + "/" + imgName ;
            File f2 = new File(imgPath);
            if (!f2.exists()) {
                f2.createNewFile();
            }
            OutputStream out = new FileOutputStream(imgPath);
            out.write(imageBytes);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgName;
    }


    /**
     * base64字符串转化成图片
     *
     * @param imgData
     *            图片编码
     * @return
     * @throws IOException
     */
    public static String GenerateImage(String filePath, String imgData) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        String imgName = UUID.randomUUID().toString() + ".jpg";
        if (imgData == null) // 图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + imgName);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        }catch (IOException e) {
            log.error("图片转换失败：" + e);
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
        return imgName;
    }


    /**
     * 图片转化成base64字符串
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("图片转换失败：" + e);
                e.printStackTrace();
            }
        }
        return encode;
    }

}