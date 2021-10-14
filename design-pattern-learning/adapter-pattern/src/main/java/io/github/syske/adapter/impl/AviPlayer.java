package io.github.syske.adapter.impl;

import io.github.syske.adapter.VideoPlayer;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-13 13:32
 */
public class AviPlayer implements VideoPlayer {

    @Override
    public void play(String fileName) {
        System.out.println("播放AVI媒体资源：" + fileName);
    }
}
