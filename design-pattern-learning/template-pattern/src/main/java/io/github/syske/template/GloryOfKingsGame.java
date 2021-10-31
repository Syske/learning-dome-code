package io.github.syske.template;

/**
 * @program: design-pattern-learning
 * @description: 王者荣耀游戏
 * @author: syske
 * @date: 2021-10-15 13:19
 */
public class GloryOfKingsGame extends GameAbstract{
    @Override
    public void initialize() {
        System.out.println("=================游戏初始化==================");
        System.out.println("初始化游戏数据");
        System.out.println("等待确认");
        System.out.println("加载游戏资源");
    }

    @Override
    public void startPlay() {
        System.out.println("=================开始游戏==================");
        System.out.println("敌人还有五秒到达战场……");
        System.out.println("First blood!  第一滴血");
        System.out.println("double kill!  双杀");
        System.out.println("triple kill!  三杀");
        System.out.println("Quadro  kill！ 四杀");
        System.out.println("Penta kill！ 五杀");
        System.out.println("Ace！ 团灭");
        System.out.println("Unstoppable！ 势不可挡");
        System.out.println("God like！ 超神");
        System.out.println("Legendary！ 传奇");
        System.out.println("shut down！ 终结");
    }

    @Override
    public void endPlay() {
        System.out.println("=================游戏结束==================");
        System.out.println("victory! 赢了");
    }
}
