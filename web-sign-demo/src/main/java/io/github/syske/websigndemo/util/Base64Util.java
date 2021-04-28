package io.github.syske.websigndemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.util.UUID;

/**
 * @program: springboot-jwt-demo
 * @description: base64工具类
 * @author: syske
 * @create: 2020-03-15 10:56
 */
public class Base64Util {
    private transient static Logger log = LoggerFactory.getLogger(Base64Util.class);

    private Base64Util() {
    }

    /**
     * 生成base64字符串
     *
     * @param sourcesStr
     * @return
     */
    public static String encryptBase64(String sourcesStr) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(sourcesStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Exception("base64加密失败", e);
        }

    }

    /**
     * base解码
     *
     * @param sourcesStr
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String sourcesStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(sourcesStr), "UTF-8");
        } catch (IOException e) {
            throw new Exception("base64解密失败", e);
        }

    }

    /**
     * <p>将base64字符解码保存文件</p>
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将base64编码转换成PDF
     *
     * @param base64String 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *                     2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *                     3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *                     4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     */
    public static void base64StringToPDF(String base64String, File file) {
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(base64String);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //创建到指定文件的输出流
            fout = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);

            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
                fout.close();
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PDF转换为Base64编码
     *
     * @param file
     * @return
     */
    public static String remotePdfToBase64(String file) {
        BASE64Encoder encoder = new BASE64Encoder();
        InputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;
        try {
            URL url = new URL(file);
            fin = url.openStream();
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null)
                    fin.close();
                if (bin != null)
                    bin.close();
                if (baos != null)
                    baos.close();
                if (bout != null)
                    bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件转化成base64字符串
     *
     * @param targetFile 文件完整路径
     * @return
     */
    public static String getFileBase64Str(File targetFile) throws Exception {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(targetFile);
            byte[] data = new byte[in.available()];
            in.read(data);
            return encoder.encode(data);
        } catch (IOException e) {
            throw new Exception("转base64出错");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("图片转换失败：" + e);
            }
        }
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgData 图片编码
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
        } catch (IOException e) {
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
     *
     * @param imgPath
     * @return
     */
    public static String getImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
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
