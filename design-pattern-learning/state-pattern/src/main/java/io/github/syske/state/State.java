package io.github.syske.state;
/**
 * @program: design-pattern-learning
 * @description: 状态接口
 * @author: syske
 * @date: 2021-10-20 13:15
 */
public interface State {
    /**
     * 改变状态的操作
     * @param context
     */
    void doAction(Context context);
}
