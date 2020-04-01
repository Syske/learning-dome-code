package io.github.syske;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * robot 实现自动化操作cmd命令
 *
 * @author syskey
 * @version v1.0
 */

public class AutoRobotCmd {
    /**
     * java 主入口
     *
     * @param args
     */

    public static void main(String args[]) {


        try {
            // 实例化机器人
            Robot robot = new Robot();
            // 先将鼠标移动到开始菜单的位置
           // robot.mouseMove(29, 1056);

          //  robot.delay(500);
            // 单击鼠标左键
           // robot.mousePress(InputEvent.BUTTON1_MASK);
            // 释放鼠标
           // robot.mouseRelease(InputEvent.BUTTON1_MASK);
            // 延时500ms
           // robot.delay(500);
            // 将鼠标移动到对应位置
          //  robot.mouseMove(29, 1021);
            // 单击鼠标左键
          //  robot.mousePress(InputEvent.BUTTON1_MASK);
            // 释放鼠标
           // robot.mouseRelease(InputEvent.BUTTON1_MASK);
            // 延时500ms
           // robot.delay(500);
			/*// 输入
			robot.keyPress(KeyEvent.VK_C);
			// 			
			robot.keyRelease(KeyEvent.VK_C);
			
			robot.delay(500);
			
			robot.keyPress(KeyEvent.VK_M);						
			// 
			robot.keyRelease(KeyEvent.VK_M);
			
			robot.delay(500);
			
			robot.keyPress(KeyEvent.VK_D);
			// 			
			robot.keyRelease(KeyEvent.VK_D);
			
			robot.delay(500);			
			
			robot.keyPress(KeyEvent.VK_D);
			// 			
			robot.keyRelease(KeyEvent.VK_D);
			
			robot.delay(500);*/
            int[] keys = {
                    KeyEvent.VK_WINDOWS,
                    KeyEvent.VK_SHIFT,
                    KeyEvent.VK_C, KeyEvent.VK_M, KeyEvent.VK_D,
                    KeyEvent.VK_ENTER, KeyEvent.VK_D, KeyEvent.VK_I,
                    KeyEvent.VK_R, KeyEvent.VK_ENTER, KeyEvent.VK_I,
                    KeyEvent.VK_P, KeyEvent.VK_C, KeyEvent.VK_O,
                    KeyEvent.VK_N, KeyEvent.VK_F, KeyEvent.VK_I,
                    KeyEvent.VK_G, KeyEvent.VK_ENTER,
                    KeyEvent.VK_E, KeyEvent.VK_X,
                    KeyEvent.VK_I, KeyEvent.VK_T,
                    KeyEvent.VK_ENTER};

            KeyUtil.pressKeys(robot, keys, 500);
        } catch (AWTException e) {
            // TODO Auto-generated catch blockig

            e.printStackTrace();
        }

    }

}
