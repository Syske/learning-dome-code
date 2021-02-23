package io.github.syske.mpyunzhongzhijava;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.SHA1;
import io.github.syske.mpyunzhongzhijava.util.SignUtil;
import org.junit.Test;

/**
 * @program: mp-yunzhongzhi-java
 * @description:
 * @author: syske
 * @create: 2021-02-22 11:10
 */
public class SignTest {
    private final String token = "59f43dcea4f6848d5cdb18de1f7831be";
    @Test
    public void testSing() throws AesException {
        String signature = "6261dd6ca98fe20e7e540e47dee894337e1db288";
        String timeStamp = "1613963207";
        String nonce = "2140840561";
        String sha1 = SHA1.getSHA1(token, timeStamp, nonce, signature);
        System.out.println(sha1);
    }
    @Test
    public void testSign() throws AesException {
        String signature = "6261dd6ca98fe20e7e540e47dee894337e1db288";
        String timeStamp = "1613963207";
        String nonce = "2140840561";
        String echoStr = "4217994856757682151";
        boolean sha1 = SignUtil.checkSignature(signature, timeStamp, nonce);
        System.out.println(sha1);
    }
}
