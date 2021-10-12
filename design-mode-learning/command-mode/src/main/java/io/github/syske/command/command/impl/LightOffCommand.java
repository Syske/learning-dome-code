/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.command.command.impl;

import io.github.syske.command.command.Command;
import io.github.syske.command.entity.Light;

/**
 * 关灯操作
 *
 * @author syske
 * @version 1.0
 * @date 2021-10-10 20:22
 */
public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }
}
