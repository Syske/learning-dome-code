package io.github.syske.iterator;

/**
 * @program: design-pattern-learning
 * @description: 容器接口
 * @author: syske
 * @date: 2021-10-18 13:09
 */
public interface Container {
    /**
     * 获取适配器
     *
     * @return
     */
    Iterator iterator();
}
