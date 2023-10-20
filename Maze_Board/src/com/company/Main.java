package com.company;
import view.SetupFrm;
import javax.swing.*;
import static view.View.FRAME;

public class Main {

    public static void main(String[] args) {
//        var game_deprecated = Game.getInstance();
//        JFrame frame = new JFrame("SetupFrm");
        FRAME.setContentPane(SetupFrm.getInstance().getMainPanel());
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.pack();
        FRAME.setVisible(true);
//        System.exit(0);
    }
}
