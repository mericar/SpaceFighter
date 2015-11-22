package spacefighter;

/**
 * Created by M on 15-09-08.
 */


import agents.Bogey;
import agents.Player;
import agents.Shot;
import resources.Constants;

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
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable, Constants {

    private Dimension d;
    private ArrayList bogeys;
    private Player player;
    private Shot shot;

    private int alienX = 350;
    private int alienY = 100;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private final String expl = "purp.jpeg";
    private final String alienpix = "purp.jpeg";
    private String gameOverMessage = "Game Over";

    private Thread animator;

    public Board()
    {
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
        ingame = true;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

        for (int i=0; i < 3; i++) {
            for (int j=0; j < 3; j++) {
                Bogey bogey = new Bogey(alienX + 18*j, alienY + 18*i);
                bogey.setImage(ii.getImage());
                bogeys.add(bogey);
            }
        }

        player = new Player();
        shot = new Shot();

        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g)
    {

        for (Object bogey1 : bogeys) {
            Bogey bogey = (Bogey) bogey1;

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
        if (shot.isVisible())
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
    }

    public void drawBombing(Graphics g) {

        for (Object bogey : bogeys) {
            Bogey a = (Bogey) bogey;

            Bogey.Missile b = a.getMissile();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void paint(Graphics g)
    {
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

    public void gameOver()
    {

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

    public void animationCycle()  {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            gameOverMessage = "Game won!";
            ingame = false;
            deaths = 0;
        }

        // player

        player.act();
        // shot
        if (player.isVisible()) {
            Iterator it = bogeys.iterator();
            int playerX = player.getX();
            int playerY = player.getY();

            while (it.hasNext()) {
                Bogey bogey = (Bogey) it.next();
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

        // shot
        if (shot.isVisible()) {
            Iterator it = bogeys.iterator();
            int shotX = shot.getX();
            int shotY = shot.getY();

            while (it.hasNext()) {
                Bogey bogey = (Bogey) it.next();
                int alienX = bogey.getX();
                int alienY = bogey.getY();

                if (bogey.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) &&
                            shotY >= (alienY) && shotY <= (alienY+ALIEN_HEIGHT) ) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        bogey.setImage(ii.getImage());
                        bogey.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;
            if (y < 0)
                shot.die();
            else shot.setY(y);
        }

        // bogeys

        for (Object bogey1 : bogeys) {
            Bogey a1 = (Bogey) bogey1;
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                for (Object bogey : bogeys) {
                    Bogey a2 = (Bogey) bogey;
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                for (Object bogey : bogeys) {
                    Bogey a = (Bogey) bogey;
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

        // bombs

        Iterator i3 = bogeys.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Bogey a = (Bogey) i3.next();
            Bogey.Missile b = a.getMissile();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {
                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {
                if ( bombX >= (playerX) &&
                        bombX <= (playerX+PLAYER_WIDTH) &&
                        bombY >= (playerY) &&
                        bombY <= (playerY+PLAYER_HEIGHT) ) {
                    ImageIcon ii =
                            new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
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

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            if (ingame)
            {
                if (e.isAltDown()) {
                    if (!shot.isVisible())
                        shot = new Shot(x, y);
                }
            }
        }
    }
}
