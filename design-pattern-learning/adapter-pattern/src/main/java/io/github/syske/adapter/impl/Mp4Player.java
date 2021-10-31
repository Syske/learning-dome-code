package io.github.syske.adapter.impl;

import io.github.syske.adapter.VideoPlayer;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-13 13:30
 */
public class Mp4Player implements VideoPlayer {

    @Override
    public void play(String fileName) {
        System.out.println("播放mp4媒体资源：" + fileName);
    }
}
