package io.github.syske.facade;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-14 13:27
 */
public class SystemFacade {
    Broom broom = new Broom();
    Kettle kettle = new Kettle();
    Computer computer = new Computer();

    public void facade() {
        broom.sweepFloor();
        kettle.boilWater();
        computer.searchVideo();
        System.out.println("开始播放电影！");
    }
}
