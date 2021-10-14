package io.github.syske.facade;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-14 13:27
 */
public class SystemFacade {
    SubSystem01 system01 = new SubSystem01();
    SubSystem02 system02 = new SubSystem02();
    SubSystem03 system03 = new SubSystem03();

    public void facade() {
        system01.method1();
        system02.method2();
        system03.method3();
    }
}
