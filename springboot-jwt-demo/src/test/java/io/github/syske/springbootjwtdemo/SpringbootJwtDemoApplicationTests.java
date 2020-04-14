package io.github.syske.springbootjwtdemo;

import io.github.syske.springbootjwtdemo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootJwtDemoApplicationTests {

    @Test
    void contextLoads() {
        String secret = "sdfsddsfdsfdsf123213";
        String token = JwtUtil.encode("user", secret, 30);
        System.out.println(token);
    }

}
