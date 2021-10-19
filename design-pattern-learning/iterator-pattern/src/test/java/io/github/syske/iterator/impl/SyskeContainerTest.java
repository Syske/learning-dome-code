package io.github.syske.iterator.impl;

import io.github.syske.iterator.Iterator;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-18 21:19
 */
public class SyskeContainerTest extends TestCase {

    public void testIterator() {
        SyskeContainer syskeContainer = new SyskeContainer();
        Iterator iterator = syskeContainer.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}