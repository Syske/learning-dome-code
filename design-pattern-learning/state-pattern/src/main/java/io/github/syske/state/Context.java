package io.github.syske.state;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-20 13:17
 */
public class Context {
    private State state;

    public Context(){}

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
