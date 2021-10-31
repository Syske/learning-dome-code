package io.github.syske.template;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-15 13:30
 */
public class TemplatePatternTest extends TestCase {

    @Test
    public void testPlay() {
        System.out.println("王者荣耀");
        GameAbstract gameAbstract = new GloryOfKingsGame();
        gameAbstract.play();

        System.out.println("\n使命召唤");
        GameAbstract callGame = new MissionCallGame();
        callGame.play();
    }
}