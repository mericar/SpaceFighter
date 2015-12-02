package gui;

import resources.Constants;

import javax.swing.*;
import java.awt.*;


public class SelectScreen extends JFrame implements Constants {

    public SelectScreen(){
        //TODO: Implement selection home screen.
        add(new StartPanel());
        setTitle("Space Fighter!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}