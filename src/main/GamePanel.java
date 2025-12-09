package main;

import java.awt.*;
import javax.swing.JPanel;
import entity.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import object.SuperObject;
import utils.CollisionChecker;
import utils.CooldownManager;
import utils.KeyHandler;
import utils.ProjectileManager;
import utils.CoordinateManager;
import utils.CrepsManager;
import utils.ScreenManagement;
import utils.StatueManager;
import utils.TileMangement;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    public UI ui = new UI(this);
    public int gameState;
    public final int titleState = -1;
    public final int playState = 0;
    public final int pauseState = 1;
    public final int deadState = 2;
    public final int winState = 3;
    public int Mx, My;
    public KeyHandler keyHandler = KeyHandler.getInstance();
    public CoordinateManager mouseTrack = CoordinateManager.getInstance();
    public ScreenManagement screenManagement = ScreenManagement.getInstance(this);
    public TileMangement tileM = TileMangement.getInstance(this);
    public Player player = Player.getInstance(this);
    public CollisionChecker collisionChecker = CollisionChecker.getInstance(this);
    public CrepsManager crepsManager = CrepsManager.getInstance(this);
    public ProjectileManager projectileManager = ProjectileManager.getInstance();
    public CooldownManager cooldownManager = CooldownManager.getInstance();
    public StatueManager statueManager = StatueManager.getInstance(this);
    public SuperObject obj[][] = new SuperObject[1][10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(Constant.screenWidth, Constant.screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Mx = e.getX();
                My = e.getY();
            }
        });
        this.gameState = titleState;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameState == titleState) {
                    for (int i = 0; i < ui.titleButtons.length; i++) {
                        if (ui.titleButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if (i == 0) {
                                gameState = playState;
                            } else if (i == 1) {
                                System.exit(0);
                            }
                        }
                    }
                } else if (gameState == pauseState) {
                    for (int i = 0; i < ui.pauseButtons.length; i++) {
                        if (ui.pauseButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if (i == 0) {
                                gameState = playState;
                            } else if (i == 1) {
                                gameState = titleState;
                                ui.commandNum = 0;
                            }
                        }
                    }
                } else if (gameState == deadState) {
                    for (int i = 0; i < ui.deadButtons.length; i++) {
                        if (ui.deadButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                        }
                    }
                } else if (gameState == winState) {
                    for (int i = 0; i < ui.winButtons.length; i++) {
                        if (ui.winButtons[i].contains(e.getX(), e.getY())) {
                            ui.commandNum = i;
                            if (i == 0) {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        });
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();

    }


    @Override
    public void run() {
        double drawInterval = 1000000000.0 / Constant.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();

            if (remainingTime > 0) {
                try {
                    Thread.sleep((long) (remainingTime / 1_000_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            nextDrawTime += drawInterval;
        }
    }

    public void update() {
        screenManagement.update();
        player.update(keyHandler);
        crepsManager.updateCreps();
        projectileManager.updateProjectiles();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, Constant.screenWidth, Constant.screenHeight);
        } else {
            tileM.draw(g2);
            player.draw(g2);
            crepsManager.drawCreps(g2);
            projectileManager.drawProjectiles(g2);
            statueManager.drawStatues(g2);
        }

        ui.draw(g2); // 👈 draws title/pause/dead/win

        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }
}
