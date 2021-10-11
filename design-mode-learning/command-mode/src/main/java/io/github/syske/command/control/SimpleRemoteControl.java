/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.command.control;

import io.github.syske.command.command.Command;

/**
 * 控制器
 *
 * @author syske
 * @version 1.0
 * @date 2021-10-10 20:19
 */
public class SimpleRemoteControl {
    Command command;

    public SimpleRemoteControl() {
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void buttonWasPressed() {
        command.execute();
    }
}

