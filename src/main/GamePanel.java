package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inGameEntity.MainCharacter;
import utils.CollisionChecker;
import utils.KeyHandler;
import utils.MouseTrack;
import utils.TileMangement;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;

    public KeyHandler keyHandler = new KeyHandler();
    public MouseTrack mouseTrack = new MouseTrack();
    public TileMangement tileM = new TileMangement(this);
    public MainCharacter mainCharacter = MainCharacter.getInstance(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public final int FPS = 60;

    public final int originalTileSize = 16;
    public final int tileSize = originalTileSize * 4;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 50; // map
    public final int maxWorldRow = 25; // map

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // Assuming 60 FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            double remainingTime = nextDrawTime - System.nanoTime();
            if (remainingTime > 0) {
                try {
                    Thread.sleep((long) (remainingTime / 1000000)); // Convert to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            nextDrawTime += drawInterval;
        }
    }

    public void update() {
        mainCharacter.update(keyHandler);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image backgroundImg;
        try {
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/res/background.png"));
        } catch (IOException | IllegalArgumentException e) {
            backgroundImg = null;
        }

        g2.drawImage(backgroundImg, 0, 0, screenWidth, screenHeight, null);
        tileM.draw(g2);
        mainCharacter.draw(g2);
        g2.dispose();
    }

}
