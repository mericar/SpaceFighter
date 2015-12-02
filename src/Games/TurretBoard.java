

package Games;

/**
 * Created by M on 15-09-08.
 */


import agents.Bogey;
import agents.Player;
import agents.Sentry;
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


public class TurretBoard extends JPanel implements Runnable, Constants {

    private Dimension d;
    private ArrayList sentries;
    private Player player;
    private Shot shot;
    private int deaths = 0;
    private boolean ingame = true;
    private final String expl = "purp.jpeg";
    private final String sentrypix = "purp.jpeg";
    private String gameOverMessage = "Game Over";

    private Thread animator;


    public TurretBoard(){
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

        sentries = new ArrayList(4);
        ingame = true;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sentrypix));

        Sentry snw = new Sentry(SENTRY_NORTHWEST);
        sentries.add(0,snw);
        Sentry sne = new Sentry(SENTRY_NORTHEAST);
        sentries.add(1,sne);
        Sentry ssw = new Sentry(SENTRY_SOUTHWEST);
        sentries.add(2,ssw);
        Sentry sse = new Sentry(SENTRY_SOUTHEAST);
        sentries.add(3,sse);

        player = new Player();
        shot = new Shot();

        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
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
        //TODO: implement animation cycle for
    }

    @Override
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