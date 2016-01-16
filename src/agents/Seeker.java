package agents;

import javax.swing.*;

/**
 * Created by mirk on 11/13/2015.
 */
public class Seeker extends Sprite {

    //TODO: Implement sprite


    private String seeker = "purp.jpeg";
    private final int H_SPACE = 4;
    private final int V_SPACE = 4;
    private Player target;

    public Seeker(int x, int y, Player p) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(seeker));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
        this.target = p;
    }



}
