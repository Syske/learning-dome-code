package io.github.syske.state;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:19
 */
public class StopState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("Context is in stop state");
        context.setState(this);
    }
}
