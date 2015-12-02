package gui;

import Games.DefenderBoard;
import resources.GameType;
import Games.Board;
import resources.Constants;

import javax.swing.*;

/**
 * Created by M on 15-09-08.
 */

public class SpaceFighter extends JFrame implements Constants {

    public SpaceFighter(GameType gt){
        switch(gt){
            case ONSLAUGHT:
                System.out.println("onslaught gametype selected");

            case DEFENDER:
                add(new DefenderBoard());
                setTitle("Space Fighter!");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setSize(BOARD_WIDTH, BOARD_HEIGHT);
                setLocationRelativeTo(null);
                setVisible(true);
                setResizable(false);

            case TURRET:
                System.out.println("turret gametype selected");
        }
    }

}