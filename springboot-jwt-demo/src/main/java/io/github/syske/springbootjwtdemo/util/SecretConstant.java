package io.github.syske.springbootjwtdemo.util;

import org.apache.shiro.codec.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-14 20:50
 */
public class SecretConstant {
    //签名秘钥 自定义
    public static final String BASE64SECRET = "C3LZA2VYLXNWCMLUZ2JVB3QTC2HPCM8TAND0";
    // 服务器的key。用于做加解密的key数据
    public static final String JWT_SECERT = "sysker-sso-jwt";

    //超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    //用于JWT加密的密匙 自定义
    public static final String DATAKEY = "C3LZA2VYLXNWCMLUZ2JVB3QTC2HPCM8TAND0";

}

