package io.github.syske.wisdom_garden.test;

import io.github.syske.interiew.wisdom_garden.ShoppingSettlement;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @program: interview-test-case
 * @description: 购物结算测试
 * @author: syske
 * @create: 2021-02-27 17:40
 */
public class ShoppingSettementTest {

    /**
     * Case A
     *
     * @throws Exception
     */
    @Test
    public void testSettlementCase1() throws Exception {
        InputStream inputStream = new FileInputStream(new File("target/test-classes/testCase1.txt"));
        System.setIn(inputStream);
        ShoppingSettlement.main(null);
    }

    /**
     * Case B
     *
     * @throws Exception
     */
    @Test
    public void setShoppingSettlementTestCase2() throws Exception {
        InputStream inputStream = new FileInputStream(new File("target/test-classes/testCase2.txt"));
        System.setIn(inputStream);
        ShoppingSettlement.main(null);
    }

    /**
     * 自定义测试用例：测试折扣过期情况
     *
     * @throws Exception
     */
    @Test
    public void setShoppingSettlementTestCase3() throws Exception {
        InputStream inputStream = new FileInputStream(new File("target/test-classes/testCase3.txt"));
        System.setIn(inputStream);
        ShoppingSettlement.main(null);
    }

    @Test
    public void setShoppingSettlementTestCase4() throws Exception {
        InputStream inputStream = new FileInputStream(new File("target/test-classes/testCase4.txt"));
        System.setIn(inputStream);
        ShoppingSettlement.main(null);
    }
}
