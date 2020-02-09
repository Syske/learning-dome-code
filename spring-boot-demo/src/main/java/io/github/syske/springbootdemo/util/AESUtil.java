package io.github.syske.springbootdemo.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @program: ydjywebshow
 * @description:
 * @author: liu yan
 * @create: 2019-11-27 13:42
 */
public class AESUtil {
    private static final String encodeRules = "ydjy";

    public AESUtil() {
    }

    /**
     * 加密
     * @param content
     * @return
     */
    public static String AESEncode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            byte[] byte_encode = content.getBytes("utf-8");
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String AES_encode = new String((new BASE64Encoder()).encode(byte_AES));
            return AES_encode;
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
        } catch (NoSuchPaddingException var11) {
            var11.printStackTrace();
        } catch (InvalidKeyException var12) {
            var12.printStackTrace();
        } catch (IllegalBlockSizeException var13) {
            var13.printStackTrace();
        } catch (BadPaddingException var14) {
            var14.printStackTrace();
        } catch (UnsupportedEncodingException var15) {
            var15.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     * @param content
     * @return
     */
    public static String AESDecode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            byte[] byte_content = (new BASE64Decoder()).decodeBuffer(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
        } catch (NoSuchPaddingException var11) {
            var11.printStackTrace();
        } catch (InvalidKeyException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        } catch (IllegalBlockSizeException var14) {
            throw new RuntimeException("兄弟，配置文件中的密码需要使用AES加密，请使用io.github.syske.springbootdemo.mybatisplus.AESUtil工具类修改这些值！");
        } catch (BadPaddingException var15) {
            var15.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
       // String decryptString = AESDecode("kFqfC5EJ1rGwonLdFCYQHA==");
        String aesEncode = AESEncode("sxlssydjy_test");
        System.out.println(aesEncode);
    }
}
