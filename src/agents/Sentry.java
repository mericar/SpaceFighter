package agents;

import javax.swing.*;

/**
 * Created by mirk on 11/13/2015.
 */
public class Sentry extends Sprite {


    private String sentry, missile = "purp.jpeg";

    public Sentry(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(missile));
        setImage(ii.getImage());

    }

}
