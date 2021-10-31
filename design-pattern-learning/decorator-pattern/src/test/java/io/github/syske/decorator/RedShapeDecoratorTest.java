package io.github.syske.decorator;

import io.github.syske.decorator.impl.Circle;
import io.github.syske.decorator.impl.Rectangle;
import org.junit.Test;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-12 13:28
 */
public class RedShapeDecoratorTest {

    @Test
    public void testDecorator() {
        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        //Shape redCircle = new RedShapeDecorator(new Circle());
        //Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("原始圆形");
        circle.draw();

        System.out.println("===========\n包装的红色圆形");
        redCircle.draw();

        System.out.println("===========\n包装的红色矩形");
        redRectangle.draw();
    }
}