package io.github.syske.decorator.impl;

import io.github.syske.decorator.Shape;

/**
 * @program: design-mode-learning
 * @description: 圆
 * @author: syske
 * @date: 2021-10-12 13:20
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }
}
