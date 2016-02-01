package agents;

/**
 * Created by M on 15-09-08.
 */

import resources.Constants;

import javax.swing.ImageIcon;


public class Bogey extends Sprite implements Constants {

    private Missile missile;
    private String shot = "purp.jpeg";
    private Player player;

    public Bogey(int x, int y, Player p) {
        this.x = x;
        this.y = y;
        this.player = p;

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
                if(this.getY() - player.getY() > 0){
                    this.setY(this.getY() - 2);
                }else {
                    this.setY(this.getY() + 1);
                }
                if(this.getX() - player.getX() > 0){
                    this.setX(this.getX() - 1);
                }else {
                    this.setX(this.getX() + 2);
                }

                if (this.getY() >= GROUND - BOMB_HEIGHT) {
                    this.setDestroyed(true);
                }
            }
        }
    }

}
