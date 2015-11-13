package spacefighter;

/**
 * Created by M on 15-09-08.
 */


import javax.swing.ImageIcon;


public class Shot extends Sprite {

    private String shot = "purp.jpeg";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}