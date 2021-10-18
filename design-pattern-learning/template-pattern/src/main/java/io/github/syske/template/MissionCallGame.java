package io.github.syske.template;

/**
 * @program: design-pattern-learning
 * @description: 使命召唤
 * @author: syske
 * @date: 2021-10-15 13:41
 */
public class MissionCallGame extends GameAbstract {
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
        System.out.println("冲锋团队竞技！");
        System.out.println("我们领先了！");
        System.out.println("目标即将完成，请再坚持一下！");
        System.out.println("目我们赢了，一大赢，小心敌人反扑！");
    }

    @Override
    public void endPlay() {
        System.out.println("=================游戏结束==================");
        System.out.println("胜利");
    }
}
