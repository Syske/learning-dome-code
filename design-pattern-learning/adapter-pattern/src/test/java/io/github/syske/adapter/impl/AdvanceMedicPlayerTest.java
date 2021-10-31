package io.github.syske.adapter.impl;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @program: design-mode-learning
 * @description:
 * @author: syske
 * @date: 2021-10-13 14:25
 */
public class AdvanceMedicPlayerTest extends TestCase {

    @Test
    public void testAdapeter() {
        AdvanceMedicPlayer audioPlayer = new AdvanceMedicPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}