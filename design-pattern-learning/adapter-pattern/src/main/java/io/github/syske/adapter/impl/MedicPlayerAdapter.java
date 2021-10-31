package io.github.syske.adapter.impl;

import io.github.syske.adapter.MedicPlayer;
import io.github.syske.adapter.VideoPlayer;
import io.github.syske.adapter.impl.AviPlayer;
import io.github.syske.adapter.impl.Mp4Player;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-13 13:52
 */
public class MedicPlayerAdapter implements MedicPlayer {
    private VideoPlayer videoPlayer;

    public MedicPlayerAdapter(String audioType){
        if(audioType.equalsIgnoreCase("avi") ){
            videoPlayer = new AviPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")){
            videoPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        videoPlayer.play(fileName);
    }
}
