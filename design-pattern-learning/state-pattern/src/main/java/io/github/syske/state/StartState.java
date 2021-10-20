package io.github.syske.state;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:18
 */
public class StartState implements State{
    @Override
    public void doAction(Context context) {
        System.out.println("Context is in start state");
        context.setState(this);
    }
}
