package io.github.syske.mpyunzhongzhijava.controller;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mp-yunzhongzhi-java
 * @description: 微信公众号token处理controller
 * @author: syske
 * @create: 2021-02-22 10:21
 */
@RestController
@RequestMapping("wx/")
public class WxTokenController {

    private final String token = "59f43dcea4f6848d5cdb18de1f7831be";
    private final String encodingAesKey = "d8j2KRmgk9euN6eUPimifDakJXgUsjpmqNZimknavS6";
    private final String appId = "wx0742b17f44f29e58";

    /**
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @RequestMapping("wxToken")
    public String checkToken(String signature, String timestamp, String nonce, String echostr) {
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("echostr:" + echostr);
        WXBizMsgCrypt wxcpt = null;
        try {

            wxcpt = new WXBizMsgCrypt(token, encodingAesKey, appId);
            String result = wxcpt.verifyUrl(signature, timestamp, nonce, echostr);
            System.out.println("result:" + result);
        } catch (AesException e) {
            e.printStackTrace();
        }


        return echostr;
    }
}
