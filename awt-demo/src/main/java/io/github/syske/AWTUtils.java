package io.github.syske;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: awt-demo
 * @description: awt工具类
 * @author: syske
 * @create: 2020-02-18 10:21
 */
public class AWTUtils {

    public static void getMouseLocation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                // TODO Auto-generated method stub
                Point point = MouseInfo.getPointerInfo().getLocation();
                System.out.println("Location:x=" + point.x + ",y=" + point.y);
            }

        }, 100, 100);
    }
}
