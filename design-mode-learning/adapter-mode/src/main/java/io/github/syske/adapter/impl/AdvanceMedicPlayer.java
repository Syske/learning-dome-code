package io.github.syske.adapter.impl;

import io.github.syske.adapter.MedicPlayer;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-13 13:59
 */
public class AdvanceMedicPlayer implements MedicPlayer {
    MedicPlayerAdapter playerAdapter;

    @Override
    public void play(String audioType, String fileName) {
        // 播放 mp3 音乐文件的内置支持
        if ("mp3".equalsIgnoreCase(audioType)) {
            System.out.println("Playing mp3 file. Name: " + fileName);
        }
        // mediaAdapter 提供了播放其他文件格式的支持
        else if ("avi".equalsIgnoreCase(audioType) || "mp4".equalsIgnoreCase(audioType)) {
            playerAdapter = new MedicPlayerAdapter(audioType);
            playerAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}
