package agents;

import resources.CoordPair;

import javax.swing.*;

/**
 * Created by mirk on 11/13/2015.
 */
public class Sentry extends Sprite {


    private String sentrypic, missile = "purp.jpeg";

    public Sentry(CoordPair cp) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(missile));
        setImage(ii.getImage());
        this.x = cp.getX_coord();
        this.y = cp.getY_coord();

    }

    private void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public class Projectile extends Sprite {

        private String projectile = "purp.jpeg";
        private boolean destroyed;

        public Projectile(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(this.getClass().getResource(projectile));
            setImage(ii.getImage());
        }
        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
