package io.github.syske.decorator.impl;

import io.github.syske.decorator.Shape;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-12 13:21
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制矩形");
    }
}
