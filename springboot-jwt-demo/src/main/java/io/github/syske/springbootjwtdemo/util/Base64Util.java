package io.github.syske.springbootjwtdemo.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @program: springboot-jwt-demo
 * @description: base64工具类
 * @author: syske
 * @create: 2020-03-15 10:56
 */
public class Base64Util {

    private Base64Util() {}

    /**
     * 生成base64字符串
     * @param sourcesStr
     * @return
     */
    public static String  encryptBase64(String sourcesStr) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(sourcesStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Exception("base64加密失败" ,e);
        }

    }

    /**
     * base解码
     * @param sourcesStr
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String sourcesStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(sourcesStr), "UTF-8");
        } catch (IOException e) {
            throw new Exception("base64解密失败" ,e);
        }

    }



    public static void main(String[] args) {
        String sourcesStr1 = "sysker-spring-boot-shiro-secret";
        try {
            String enStr = encryptBase64(sourcesStr1);
            System.out.println(enStr);
            System.out.println(decryptBase64(enStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
