package gui;

import spacefighter.Board;
import resources.Constants;

import javax.swing.*;

/**
 * Created by M on 15-09-08.
 */

public class SpaceFighter extends JFrame implements Constants {

    public SpaceFighter(){

        add(new Board());
        setTitle("Space Fighter!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}