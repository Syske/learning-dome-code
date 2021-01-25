package io.gtihub.syske.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @program: encrypt-util
 * @description: md5工具类
 * @author: syske
 * @create: 2020-02-18 14:06
 */
public class MD5Util {

        public static void main(String[] args) {
            String password = encrypt("123456adfaf");
            if(encrypt("123456adfaf").equals(password)) {
                System.out.println("密码正确");
            } else {
                System.out.println("密码错误");
            }
        }

        private static String encrypt(String password) {
            String passwordMd5 = null;
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] bytes = md5.digest(password.getBytes("utf-8"));
                passwordMd5 = HEXUtil.toHexString(bytes);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return passwordMd5;
        }


}
