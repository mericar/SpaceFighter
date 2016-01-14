package agents;

/**
 * Created by M on 15-09-08.
 */
import resources.Direction;

import javax.swing.*;


public class Shot extends Sprite {

    private String shot = "purp.jpeg";
    private final int H_SPACE = 4;
    private final int V_SPACE = 4;
    private Direction shotDirection;

    public Shot(Direction d) {
        this.shotDirection = d;
    }

    public Shot(int x, int y, Direction direction) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
        this.shotDirection = direction;
    }

    public void move() {

        int x;
        int y;

        switch (this.shotDirection){

            case UP:
                y = this.getY();
                y -= 4;
                if (y < 0) {
                    this.die();
                } else {
                    this.setY(y);
                }
                break;

            case DOWN:
                y = this.getY();
                y += 4;
                if (y > 1000) {
                    this.die();
                } else {
                    this.setY(y);
                }
                break;

            case LEFT:
                x = this.getX();
                x -= 4;
                if (x < 0) {
                    this.die();
                } else {
                    this.setX(x);
                }
                break;

            case RIGHT:
                x = this.getX();
                x += 4;
                if (x > 1500) {
                    this.die();
                } else {
                    this.setX(x);
                }
                break;
        }
    }


}