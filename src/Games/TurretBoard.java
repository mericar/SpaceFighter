

package Games;

/**
 * Created by M on 15-09-08.
 */


import agents.Bogey;
import agents.Player;
import agents.Sentry;
import agents.Shot;
import resources.Constants;
import resources.Direction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class TurretBoard extends JPanel implements Runnable, Constants {

    private Dimension d;
    private ArrayList sentries;
    private ArrayList shots;
    private Player player;
    private Shot shot;
    private int deaths = 0;
    private boolean ingame = true;
    private final String expl = "purp.jpeg";
    private final String sentrypic = "purp.jpeg";
    private String gameOverMessage = "Game Over";

    private Thread animator;

    private Shot shotUp;
    private Shot shotDown;
    private Shot shotLeft;
    private Shot shotRight;


    public TurretBoard()
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


    public void gameInit(){

        sentries = new ArrayList();
        ingame = true;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sentrypic));

        Sentry snw = new Sentry(SENTRY_NORTHWEST);
        sentries.add(0,snw);
        Sentry sne = new Sentry(SENTRY_NORTHEAST);
        sentries.add(1,sne);
        Sentry ssw = new Sentry(SENTRY_SOUTHWEST);
        sentries.add(2,ssw);
        Sentry sse = new Sentry(SENTRY_SOUTHEAST);
        sentries.add(3,sse);

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


    public void drawSentries(Graphics g)
    {
        for (Object sentry : sentries) {
            Sentry s = (Sentry) sentry;

            if (s.isVisible()) {
                g.drawImage(s.getImage(), s.getX(), s.getY(), this);
            }

            if (s.isDying()) {
                s.die();
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
        }
    }

    public void drawProjectile(Graphics g) {

        for (Object sentry : sentries) {
            Sentry sentry1 = (Sentry) sentry;

            Sentry.Projectile p = sentry1.getProjectile();

            if (!p.isDestroyed()) {
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(Color.darkGray);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {
            drawSentries(g);
            drawPlayer(g);
            drawShot(g);
            drawProjectile(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {
        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(180, 10, 0));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 100);
        Font fontStyle = new Font("Courier", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(fontStyle);

        g.setColor(Color.white);
        g.setFont(fontStyle);
        g.drawString(gameOverMessage, (BOARD_WIDTH - metr.stringWidth(gameOverMessage)) / 2,
                BOARD_WIDTH / 2 + 20);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        animator = null;
        deaths = 0;
        gameInit();
    }



    public void animationCycle(){
        //TODO: implement animation cycle

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            gameOverMessage = "Game won!";
            ingame = false;
            deaths = 0;
        }

        // player

        player.act();
        // shot
        if (player.isVisible()) {
            Iterator it = sentries.iterator();
            int playerX = player.getX();
            int playerY = player.getY();

            while (it.hasNext()) {
                Sentry sentry = (Sentry) it.next();
                int sentryX = sentry.getX();
                int sentryY = sentry.getY();

                if (sentry.isVisible()) {
                    if (playerX >= (sentryX) && playerX <= (sentryX + ALIEN_WIDTH) &&
                            playerY >= (sentryY) && playerY <= (sentryY+ALIEN_HEIGHT) ) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                }
            }
        }

        // shot
        for (Object s : shots) {
            Shot shot = (Shot) s;

            if (shot.isVisible()) {
                Iterator it = sentries.iterator();
                int shotX = shot.getX();
                int shotY = shot.getY();

                while (it.hasNext()) {
                    Sentry sentry = (Sentry) it.next();
                    Sentry.Projectile p = sentry.getProjectile();

                    int pX = p.getX();
                    int pY = p.getY();
                    int sentryX = sentry.getX();
                    int sentryY = sentry.getY();

                    if (p.isVisible() && shot.isVisible()) {
                        if (shotX >= (pX) && shotX <= (pX + ALIEN_WIDTH) &&
                                shotY >= (pY) && shotY <= (pY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            p.setImage(ii.getImage());
                            p.setDying(true);
                            shot.setDying(true);
                            p.setDestroyed(true);
                        }
                    }

                    if (sentry.isVisible() && shot.isVisible()) {
                        if (shotX >= (sentryX) && shotX <= (sentryX + ALIEN_WIDTH) &&
                                shotY >= (sentryY) && shotY <= (sentryY+ALIEN_HEIGHT) ) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            sentry.setImage(ii.getImage());
                            sentry.setDying(true);

                            deaths++;
                            shot.setDying(true);
                        }
                    }
                }
                //extracted movement code into shot method
                shot.move();
            }
        }

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