/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.command.command.impl;

import io.github.syske.command.entity.Light;
import io.github.syske.command.command.Command;

/**
 * 开灯命令
 *
 * @author syske
 * @version 1.0
 * @date 2021-10-10 20:11
 */
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
