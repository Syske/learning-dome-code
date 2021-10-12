package io.github.syske.decorator;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-12 13:26
 */
public abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}
