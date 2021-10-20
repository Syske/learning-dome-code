package io.github.syske.composite;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-19 21:13
 */
public class MenuComponentTest extends TestCase {
    @Test
    public void testComponent() {

        // 叶节点
        MenuItem slr = new MenuItem("烧鹿茸", "好吃美味，价格实惠", Boolean.FALSE, 180.0);
        MenuItem sxz = new MenuItem("烧熊掌", "好吃美味，价格实惠", Boolean.FALSE, 190.0);

        MenuItem hsr = new MenuItem("红烧肉", "好吃美味，价格实惠", Boolean.FALSE, 36.0);
        MenuItem hsqz = new MenuItem("红烧茄子", "好吃美味，价格实惠", Boolean.TRUE, 14.0);
        MenuItem hsjk = new MenuItem("红烧鸡块", "好吃美味，价格实惠", Boolean.FALSE, 38.0);
        MenuItem yxrs = new MenuItem("鱼香肉丝", "好吃美味，价格实惠", Boolean.FALSE, 22.0);
        MenuItem ssbc = new MenuItem("手撕包菜", "好吃美味，价格实惠", Boolean.TRUE, 12.0);

        // 组合节点
        Menu menu = new Menu("家常菜", "美味家常菜");
        menu.add(hsr);
        menu.add(hsqz);
        menu.add(hsjk);
        menu.add(yxrs);
        menu.add(ssbc);
        // 子节点print方法
        slr.print();
        sxz.print();
        // 组合节点print方法
        menu.print();

    }
}