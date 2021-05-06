package io.gtihub.syske.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * @program: encrypt-util
 * @description: MD5withRSA和SHA1withRSA签名
 * @author: syske
 * @create: 2020-02-18 14:01
 */

public class DigitalSignature {
    private static Logger logger = LoggerFactory.getLogger(DigitalSignature.class);

    public static void main(String[] args) throws Exception {
        String content = "study hard and make progress everyday";
        logger.debug("content :" + content);

        KeyPair keyPair = getKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String md5Sign = getMd5Sign(content, privateKey);
        logger.debug("sign with md5 and rsa :" + md5Sign);
        boolean md5Verifty = verifyWhenMd5Sign(content, md5Sign, publicKey);
        logger.debug("verify sign with md5 and rsa :" + md5Verifty);

        String sha1Sign = getSha1Sign(content, privateKey);
        logger.debug("签名长度：" + sha1Sign.length());
        logger.debug("sign with sha1 and rsa :" + sha1Sign);
        boolean sha1Verifty = verifyWhenSha1Sign(content, sha1Sign, publicKey);
        logger.debug("verify sign with sha1 and rsa :" + sha1Verifty);

    }

    // 生成密钥对
    static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512); // 可以理解为：加密后的密文长度，实际原文要小些 越大 加密解密越慢
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }

    // 用md5生成内容摘要，再用RSA的私钥加密，进而生成数字签名
    static String getMd5Sign(String content, PrivateKey privateKey)
            throws Exception {
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return HEXUtil.bytesToHex(signs);
    }

    // 对用md5和RSA私钥生成的数字签名进行验证
    static boolean verifyWhenMd5Sign(String content, String sign,
                                     PublicKey publicKey) throws Exception {
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(contentBytes);
        return signature.verify(HEXUtil.hexToByteArray(sign));
    }

    // 用sha1生成内容摘要，再用RSA的私钥加密，进而生成数字签名
    static String getSha1Sign(String content, PrivateKey privateKey)
            throws Exception {
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return HEXUtil.bytesToHex(signs);
    }

    // 对用md5和RSA私钥生成的数字签名进行验证
    static boolean verifyWhenSha1Sign(String content, String sign,
                                      PublicKey publicKey) throws Exception {
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(contentBytes);

        return signature.verify(HEXUtil.hexToByteArray(sign));
    }
}
