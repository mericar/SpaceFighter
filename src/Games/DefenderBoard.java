
package Games;

/**
 * Created by M on 15-09-08.
 */


import agents.Bogey;
import agents.Player;
import agents.Shot;
import resources.Constants;
import resources.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class DefenderBoard extends JPanel implements Runnable, Constants {

    private Dimension d;
    private ArrayList bogeys;
    private ArrayList swarm;
    public ArrayList shots;
    private Player player;
    //private Shot shot;

    private int alienX = 350;
    private int alienY = 100;
    private int swarmerX = 800;
    private int swarmerY = 50;
    private int direction = dxleft;
    private int deaths = 0;

    private boolean ingame = true;
    private final String expl = "purp.jpeg";
    private final String alienpix = "purp.jpeg";
    private String gameOverMessage = "Game Over";

    private Thread animator;

    private Shot shotUp;
    private Shot shotDown;
    private Shot shotLeft;
    private Shot shotRight;



    public DefenderBoard()
    {
        this.shots = new ArrayList();
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        bogeys = new ArrayList();
        swarm = new ArrayList();
        ingame = true;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        for (int i=0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Bogey bogey = new Bogey(alienX + 18 * j, alienY + 18 * i);
                bogey.setImage(ii.getImage());
                bogeys.add(bogey);
            }
        }
        for (int i=0; i < 4; i++) {
            for (int j=0; j < 3; j++) {
                Bogey bogey = new Bogey(swarmerX + 21*j, swarmerY + 21*i);
                bogey.setImage(ii.getImage());
                swarm.add(bogey);
            }
        }

        player = new Player();

        shotUp = new Shot(Direction.UP);
        shotDown = new Shot(Direction.DOWN);
        shotLeft = new Shot(Direction.LEFT);
        shotRight = new Shot(Direction.RIGHT);
        shots.add(shotDown);
        shots.add(shotUp);
        shots.add(shotRight);
        shots.add(shotLeft);


        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        for (Object bogey1 : bogeys) {
            Bogey bogey = (Bogey) bogey1;
            if (bogey.isVisible()) {
                g.drawImage(bogey.getImage(), bogey.getX(), bogey.getY(), this);
            }
            if (bogey.isDying()) {
                bogey.die();
            }
        }

        for (Object bogey2 : swarm) {
            Bogey bogey = (Bogey) bogey2;
            if (bogey.isVisible()) {
                g.drawImage(bogey.getImage(), bogey.getX(), bogey.getY(), this);
            }
            if (bogey.isDying()) {
                bogey.die();
            }
        }

    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            gameOverMessage = "YOU HAVE BEEN SLAIN BY LORD XORLOTHOL!!!";
            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {

        for (Object s : shots) {
            Shot shot = (Shot) s;
            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
            if (shot.isDying()){
                shot.die();
            }
        }
    }

    public void drawBombing(Graphics g) {

        for (Object bogey : bogeys) {
            Bogey a = (Bogey) bogey;
            Bogey.Missile b = a.getMissile();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }

        for (Object bogey : swarm) {
            Bogey a = (Bogey) bogey;
            Bogey.Missile b = a.getMissile();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(180, 10, 0));
        g.fillRect(50, BOARD_HEIGHT / 2 - 50, BOARD_WIDTH - 100, 100);
        Font fontStyle = new Font("Courier", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(fontStyle);

        g.setColor(Color.white);
        g.setFont(fontStyle);
        g.drawString(gameOverMessage, (BOARD_WIDTH - metr.stringWidth(gameOverMessage)) / 2,
                BOARD_HEIGHT / 2);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        animator = null;
        deaths = 0;
        gameInit();
    }

    public void animationCycle()  {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            gameOverMessage = "Game won!";
            ingame = false;
            deaths = 0;
        }


        // player
        player.act();

        if (player.isVisible()) {
            Iterator bogeyIt1 = bogeys.iterator();
            Iterator bogeyIt2 = swarm.iterator();
            int playerX = player.getX();
            int playerY = player.getY();

            while (bogeyIt1.hasNext()) {
                Bogey bogey = (Bogey) bogeyIt1.next();
                int alienX = bogey.getX();
                int alienY = bogey.getY();

                if (bogey.isVisible()) {
                    if (playerX >= (alienX) && playerX <= (alienX + ALIEN_WIDTH) &&
                            playerY >= (alienY) && playerY <= (alienY+ALIEN_HEIGHT) ) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                }
            }

            while (bogeyIt2.hasNext()) {
                Bogey bogey = (Bogey) bogeyIt2.next();
                int alienX = bogey.getX();
                int alienY = bogey.getY();

                if (bogey.isVisible()) {
                    if (playerX >= (alienX) && playerX <= (alienX + ALIEN_WIDTH) &&
                            playerY >= (alienY) && playerY <= (alienY+ALIEN_HEIGHT) ) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                }
            }
        }

        // shots are looped through:
        for (Object s : shots) {

            Shot shot = (Shot) s;

            if (shot.isVisible()) {
                Iterator bogeyIt = bogeys.iterator();
                Iterator swarmIt = swarm.iterator();

                int shotX = shot.getX();
                int shotY = shot.getY();

                while (bogeyIt.hasNext()) {
                    Bogey bogey = (Bogey) bogeyIt.next();
                    Bogey.Missile b = bogey.getMissile();

                    int bombX = b.getX();
                    int bombY = b.getY();
                    int alienX = bogey.getX();
                    int alienY = bogey.getY();

                    if (b.isVisible() && shot.isVisible()) {
                        if (shotX >= (bombX) && shotX <= (bombX + ALIEN_WIDTH) &&
                                shotY >= (bombY) && shotY <= (bombY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            b.setImage(ii.getImage());
                            b.setDying(true);
                            shot.setDying(true);
                            b.setDestroyed(true);
                        }
                    }

                    if (bogey.isVisible() && shot.isVisible()) {
                        if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) &&
                                shotY >= (alienY) && shotY <= (alienY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            bogey.setImage(ii.getImage());
                            bogey.setDying(true);
                            deaths++;
                            shot.setDying(true);
                        }
                    }
                }
                while (swarmIt.hasNext()) {
                    Bogey bogey = (Bogey) swarmIt.next();
                    Bogey.Missile b = bogey.getMissile();

                    int bombX = b.getX();
                    int bombY = b.getY();
                    int alienX = bogey.getX();
                    int alienY = bogey.getY();

                    if (b.isVisible() && shot.isVisible()) {
                        if (shotX >= (bombX) && shotX <= (bombX + ALIEN_WIDTH) &&
                                shotY >= (bombY) && shotY <= (bombY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            b.setImage(ii.getImage());
                            b.setDying(true);
                            shot.setDying(true);
                            b.setDestroyed(true);
                        }
                    }

                    if (bogey.isVisible() && shot.isVisible()) {
                        if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) &&
                                shotY >= (alienY) && shotY <= (alienY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            bogey.setImage(ii.getImage());
                            bogey.setDying(true);
                            deaths++;
                            shot.setDying(true);
                        }
                    }
                }
            //extracted movement code into shot method
            shot.move();
            }
        // end of for loop
        }



        // bogeys
        for (Object bogey1 : bogeys) {
            Bogey a1 = (Bogey) bogey1;
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = dxleft;
                for (Object bogey : bogeys) {
                    Bogey a2 = (Bogey) bogey;
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = dxright;

                for (Object bogey : bogeys) {
                    Bogey a = (Bogey) bogey;
                    a.setY(a.getY() + GO_DOWN);
                }
            }

        }

        for (Object swarmer : swarm) {
            Bogey a1 = (Bogey) swarmer;
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = dxleft;
                for (Object swarmer2 : swarm) {
                    Bogey a2 = (Bogey) swarmer2;
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = dxright;

                for (Object swarmer3 : swarm) {
                    Bogey a = (Bogey) swarmer3;
                    a.setY(a.getY() + GO_DOWN);
                }
            }

        }



        for (Object bogey1 : bogeys) {
            Bogey bogey = (Bogey) bogey1;

            if (bogey.isVisible()) {
                int y = bogey.getY();
                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    gameOverMessage = "Invasion!";
                }
                bogey.act(direction);
            }
        }
        for (Object swarmer : swarm) {
            Bogey bogey = (Bogey) swarmer;

            if (bogey.isVisible()) {
                int y = bogey.getY();
                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    gameOverMessage = "Invasion!";
                }
                bogey.act(direction);
            }
        }



        // bombs
        Iterator bogeyIt = bogeys.iterator();
        Iterator swarmIt = swarm.iterator();
        Random generator = new Random();
        Random generatorS = new Random();

        while (bogeyIt.hasNext()) {
            int shot = generator.nextInt(10);
            Bogey a = (Bogey) bogeyIt.next();
            Bogey.Missile m = a.getMissile();
            if (shot == CHANCE && a.isVisible() && m.isDestroyed()) {
                m.setDestroyed(false);
                m.setX(a.getX());
                m.setY(a.getY());
            }

            int missileX = m.getX();
            int missileY = m.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !m.isDestroyed()) {
                if ( missileX >= (playerX) &&
                        missileX <= (playerX+PLAYER_WIDTH) &&
                        missileY >= (playerY) &&
                        missileY <= (playerY+PLAYER_HEIGHT) ) {
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    m.setDestroyed(true);
                }
            }
            m.move();
        }

        while (swarmIt.hasNext()) {
            int shot = generatorS.nextInt(10);
            Bogey a = (Bogey) swarmIt.next();
            Bogey.Missile m = a.getMissile();
            if (shot == CHANCE && a.isVisible() && m.isDestroyed()) {
                m.setDestroyed(false);
                m.setX(a.getX());
                m.setY(a.getY());
            }

            int missileX = m.getX();
            int missileY = m.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !m.isDestroyed()) {
                if ( missileX >= (playerX) &&
                        missileX <= (playerX+PLAYER_WIDTH) &&
                        missileY >= (playerY) &&
                        missileY <= (playerY+PLAYER_HEIGHT) ) {
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    m.setDestroyed(true);
                }
            }
            m.move();
        }
        // End Animation Cycle
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }



    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            if (ingame) {


                if (key == KeyEvent.VK_W) {
                    for (Object s : shots) {
                        Shot shot = (Shot) s;

                        if (!shot.isVisible()){
                            shot = new Shot(x, y, Direction.UP);
                            shots.add(shot);
                        }
                    }
                }
                if (key == KeyEvent.VK_A) {
                    for (Object s : shots) {
                        Shot shot = (Shot) s;

                        if (!shot.isVisible()){
                            shot = new Shot(x, y, Direction.LEFT);
                            shots.add(shot);
                        }
                    }
                }
                if (key == KeyEvent.VK_S) {
                    for (Object s : shots) {
                        Shot shot = (Shot) s;

                        if (!shot.isVisible()){
                            shot = new Shot(x, y, Direction.DOWN);
                            shots.add(shot);
                        }
                    }
                }
                if (key == KeyEvent.VK_D) {
                    for (Object s : shots) {
                        Shot shot = (Shot) s;

                        if (!shot.isVisible()){
                            shot = new Shot(x, y, Direction.RIGHT);
                            shots.add(shot);
                        }
                    }
                }

            }

        }
    }


}
