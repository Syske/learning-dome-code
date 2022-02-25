/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-09 22:11
 */
public class RunnableDemo extends Observable implements Runnable {
    private static Logger logger = Logger.getLogger("RunnableDemo");
    @Override
    public void run() {
        System.out.println("我是继承了Thread，并实现了Runnable的类");
    }

//    public static void main(String[] args) {
//        new Thread(new RunnableDemo()).start();
//    }

    public static void main(String[] args) {
        int initSize = 100;
        List<String> stringList = new ArrayList<>(initSize);
        for (int i = 0; i < initSize; i++) {
            stringList.add(String.format("%d%s", i, "str"));
        }
        // for item
        long startTimeForItem= System.currentTimeMillis();
        for (String s : stringList) {
            System.out.println(s);
        }
        long endTimeForItem= System.currentTimeMillis();

        // for i
        long startTimeForI= System.currentTimeMillis();
        for (int i = 0; i < stringList.size(); i++) {
            System.out.println(stringList.get(i));
        }
        long endTimeForI= System.currentTimeMillis();

        // forEach
        long startTimeForEach = System.currentTimeMillis();
        stringList.forEach(logger::info);
        long endTImeForEach = System.currentTimeMillis();

        // while
        long startTimeForWhile = System.currentTimeMillis();
        Iterator<String> iterator = stringList.iterator();
        while (iterator.hasNext()) {
            logger.info(iterator.next());
        }
        long endTImeForWhile = System.currentTimeMillis();
        System.out.printf("List为%d时，forEach遍历用时：%d%n", initSize, (endTImeForEach - startTimeForEach));
        System.out.printf("List为%d时，forItem遍历用时：%d%n", initSize, (endTimeForItem - startTimeForItem));
        System.out.printf("List为%d时，forI遍历用时：%d%n", initSize, (endTimeForI - startTimeForI));
        System.out.printf("List为%d时，while遍历用时：%d%n", initSize, (endTImeForWhile - startTimeForWhile));
    }
}
