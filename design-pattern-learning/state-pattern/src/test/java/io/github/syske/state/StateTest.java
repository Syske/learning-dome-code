package io.github.syske.state;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:20
 */
public class StateTest extends TestCase {
    @Test
    public void testState() {
        Context context = new Context();

        StartState startState = new StartState();
        startState.doAction(context);


        StopState stopState = new StopState();
        stopState.doAction(context);

    }
}