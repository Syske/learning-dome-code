package io.github.syske.iterator.impl;

import io.github.syske.iterator.Iterator;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-18 13:28
 */
public class SyskeContainerTest extends TestCase {

    @Test
    public void testIterator() {
        SyskeContainer syskeContainer = new SyskeContainer();
        Iterator iterator = syskeContainer.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}