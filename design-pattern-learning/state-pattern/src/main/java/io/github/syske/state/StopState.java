package io.github.syske.state;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:19
 */
public class StopState implements State {

    private String name;

    public StopState() {
        this.name = "stop";
    }

    @Override
    public void doAction(Context context) {
        System.out.println("Context is in stop state");
        context.setState(this);
        System.out.println(context);
    }

    @Override
    public String toString() {
        return "StopState{" +
                "name='" + name + '\'' +
                '}';
    }
}
