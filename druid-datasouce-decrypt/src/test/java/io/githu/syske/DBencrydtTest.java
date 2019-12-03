package io.githu.syske;

import org.junit.jupiter.api.Test;

/**
 * @program: druid-datasouce-decrypt
 * @description:
 * @author: liu yan
 * @create: 2019-12-02 18:34
 */
public class DBencrydtTest {
    @Test
    public void test() {
        String[] args = {"root"};
        try {
            com.alibaba.druid.filter.config.ConfigTools.main(args);
        } catch (Exception e) {

        }

        System.out.println();
    }
}
