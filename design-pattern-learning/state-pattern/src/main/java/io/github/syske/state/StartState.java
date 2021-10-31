package io.github.syske.state;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:18
 */
public class StartState implements State{

    private String name;

    public StartState() {
        this.name = "start";
    }

    @Override
    public void doAction(Context context) {
        System.out.println("Context is in start state");
        context.setState(this);
        System.out.println(context);
    }

    @Override
    public String toString() {
        return "StartState{" +
                "name='" + name + '\'' +
                '}';
    }
}
