package io.github.syske.facade;

import org.junit.Test;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-14 13:29
 */
public class SystemFacadeTest {
    @Test
    public void testFacade() {
        SystemFacade facade = new SystemFacade();
        facade.facade();
    }

}