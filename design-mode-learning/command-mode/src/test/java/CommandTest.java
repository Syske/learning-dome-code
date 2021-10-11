/* Copyright © 2021 syske. All rights reserved. */

import io.github.syske.command.command.impl.LightOffCommand;
import io.github.syske.command.command.impl.LightOnCommand;
import io.github.syske.command.control.SimpleRemoteControl;
import io.github.syske.command.entity.Light;
import org.junit.Test;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-10 20:23
 */
public class CommandTest {
    @Test
    public void testCommand() {
        SimpleRemoteControl control = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand onCommand = new LightOnCommand(light);
        LightOffCommand offCommand = new LightOffCommand(light);
        // 开灯
        control.setCommand(onCommand);
        control.buttonWasPressed();
        // 关灯操作
        control.setCommand(offCommand);
        control.buttonWasPressed();
    }
}
