package io.github.syske.springbootjwtdemo.sevice;

import com.auth0.jwt.interfaces.Claim;
import io.github.syske.springbootjwtdemo.dao.entity.ReturnEntity;
import io.github.syske.springbootjwtdemo.dao.entity.User;
import io.github.syske.springbootjwtdemo.exception.AuthorizationException;
import io.github.syske.springbootjwtdemo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description: Jwtservice实现
 * @author: syske
 * @create: 2020-03-14 22:16
 */
@Service
public class JwtService {
    private static Logger logger = LoggerFactory.getLogger(JwtService.class);
    // 过期时间30分钟
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * Description:登录获取token
     *
     * @param user user
     * @return java.lang.String
     * @author sysker
     * @date 2019/3/4 18:45
     */
    public String login(User user) {
        //进行登录校验
        try {
            if (user.getUsername().equalsIgnoreCase(user.getPassword())) {
                return this.generateNewJwt(user.getUsername());
            } else {
                logger.info("账号密码错误:{}{}", user.getUsername(), user.getPassword());
                throw new AuthorizationException("账号密码错误");
            }
        } catch (Exception e) {
            logger.info("账号密码错误:{},{}", user.getUsername(), user.getPassword());
            throw new AuthorizationException(e, "账号密码错误");
        }
    }

    /**
     * 过期时间小于半小时，返回新的jwt，否则返回原jwt
     *
     * @param jwt
     * @return
     */
    public String refreshJwt(String jwt) {
        String secret = (String) redisUtil.get(jwt);
        Map<String, Claim> map = JwtUtil.decode(jwt, secret);
        if (map.get("exp").asLong() * 1000 - System.currentTimeMillis() / 1000 < 30 * 60 * 1000) {
            return this.generateNewJwt(map.get("name").asString());
        } else {
            return jwt;
        }
    }


    /**
     * Description: 生成新的jwt,并放入jwtMap中
     *
     * @return java.lang.String
     * @author sysker
     * date 2019/3/5 10:44
     */
    private String generateNewJwt(String username) {
        String data = SecretConstant.DATAKEY + username + UUIDUtil.getUUIDStr();
        logger.debug("创建密钥加密前data：", data);
        String secretEncrypted = null;
        try {
            secretEncrypted = Base64Util.encryptBase64(AESSecretUtil.encryptToStr(data,
                    SecretConstant.BASE64SECRET));
        } catch (Exception e) {
            logger.error("base64加密失败：", e);
            throw new AuthorizationException("未知错误");
        }
        logger.debug("密钥加密后data：", secretEncrypted);
        String token = JwtUtil.encode(username, secretEncrypted, EXPIRE_TIME);
        logger.debug("创建token:", token);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, secretEncrypted, EXPIRE_TIME);
        return token;
    }

    /**
     * Description:检查jwt有效性
     *
     * @return Boolean
     * @author sysker
     * @date 2019/3/4 18:47
     */
    public ReturnEntity checkJwt(String jwt) {
        String secret = (String) redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + jwt);
        JwtUtil.decode(jwt, secret);
        return ReturnEntity.successResult(1, true);
    }

    /**
     * Description: 作废token，使该jwt失效
     *
     * @author sysker
     * @date 2019/3/4 19:58
     */
    public void inValid(String jwt) {
        redisUtil.del(jwt);
    }
}
