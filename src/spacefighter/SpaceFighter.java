package spacefighter;

import javax.swing.*;

/**
 * Created by M on 15-09-08.
 */

public class SpaceFighter extends JFrame implements Commons {

    public SpaceFighter(){

        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}