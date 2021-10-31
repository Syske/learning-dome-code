package io.github.syske.iterator;

/**
 * @program: design-pattern-learning
 * @description: 适配器接口
 * @author: syske
 * @date: 2021-10-18 13:08
 */
public interface Iterator {
    /**
     * 是否存在下一个元素
     * @return
     */
    boolean hasNext();

    /**
     * 返回下一个元素
     *
     * @return
     */
    Object next();
}
