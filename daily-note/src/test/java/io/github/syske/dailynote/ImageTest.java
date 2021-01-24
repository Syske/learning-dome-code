package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            cg.graphicsGeneration("D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg","D:\\share.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
