package io.github.syske;

import java.awt.Robot;

/**
 * 键盘事件的操作类
 * @author syskey
 *
 */

public class KeyUtil {

	/**
	 * 键盘输入方法
	 *
	 * @author syskey
	 * @param robot 机器人类
	 * @param keys 输入键盘的键值
	 * @param delay 延迟时间
	 */

	public static void pressKeys(Robot robot,int[] keys,int delay){
		// 传入键值、延时时长 ,循环便利数组并赋值
		for(int i = 0; i <keys.length;i++){
			robot.keyPress(keys[i]);
			robot.keyRelease(keys[i]);
			robot.delay(delay);
		}

		/*for(int i:keys){
			robot.keyPress(keys[i]);
			robot.keyRelease(keys[i]);
			robot.delay(delay);
		}*/


	}

}
