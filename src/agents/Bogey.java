package agents;

/**
 * Created by M on 15-09-08.
 */

import resources.Constants;

import javax.swing.ImageIcon;


public class Bogey extends Sprite implements Constants {

    private Missile missile;
    private String shot = "purp.jpeg";

    public Bogey(int x, int y) {
        this.x = x;
        this.y = y;

        missile = new Missile(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Missile getMissile() {
        return missile;
    }


    public class Missile extends Sprite {

        private String bomb = "purp.jpeg";
        private boolean destroyed;

        public Missile(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }

        public void move(){
            if (!this.isDestroyed()) {
                this.setY(this.getY() + 1);
                if (this.getY() >= GROUND - BOMB_HEIGHT) {
                    this.setDestroyed(true);
                }
            }
        }
    }

}
