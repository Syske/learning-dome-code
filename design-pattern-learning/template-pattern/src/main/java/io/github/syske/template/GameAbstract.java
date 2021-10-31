package io.github.syske.template;

/**
 * @program: design-pattern-learning
 * @description: 游戏抽象类
 * @author: syske
 * @date: 2021-10-15 13:11
 */
public abstract class GameAbstract {
    /**
     * 初始化操作
     */
    abstract void initialize();

    /**
     * 开始游戏
     */
    abstract void startPlay();

    /**
     * 游戏结束
     */
    abstract void endPlay();

    /**
     * 模板
     */
    public final void play(){

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }
}
