package io.github.syske;

import javax.swing.*;

public class WindowCreate {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Hello Swing");
        JLabel label = new JLabel("label text");
        jFrame.add(label);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
    }

}
