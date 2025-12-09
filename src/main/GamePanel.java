package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Player;
import java.awt.Toolkit;
import utils.CollisionChecker;
import utils.CooldownManager;
import utils.KeyHandler;
import utils.MouseManager;
import utils.ProjectileManager;
import utils.CoordinateManager;
import utils.EnemiesManager;
import utils.ScreenManagement;
import utils.StatueManager;
import utils.TileMangement;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    public MouseManager mouseManager = MouseManager.getInstance();
    public KeyHandler keyHandler = KeyHandler.getInstance();
    public CoordinateManager mouseTrack = CoordinateManager.getInstance();
    public ScreenManagement screenManagement = ScreenManagement.getInstance(this);
    public TileMangement tileM = TileMangement.getInstance(this);
    public Player player = Player.getInstance(this);
    public CollisionChecker collisionChecker = CollisionChecker.getInstance(this);
    public EnemiesManager enemiesManager = EnemiesManager.getInstance(this);
    public ProjectileManager projectileManager = ProjectileManager.getInstance();
    public CooldownManager cooldownManager = CooldownManager.getInstance();
    public StatueManager statueManager = StatueManager.getInstance(this);
    public CardChoosingState cardChoosingState = CardChoosingState.getInstance(this);

    public enum GameState {
        PLAYING,
        PAUSED,
        CARD_CHOOSING
    }

    public GameState gameState = GameState.PLAYING;

    public GamePanel() {
        this.setPreferredSize(new Dimension(Constant.screenWidth, Constant.screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseManager);
        this.setFocusable(true);
        this.setBackground(java.awt.Color.BLACK);
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
        if (gameState == GameState.PLAYING) {
            screenManagement.update();
            player.update(keyHandler);
            enemiesManager.updateEnemies();
            projectileManager.updateProjectiles();
        } else if (gameState == GameState.CARD_CHOOSING) {
            // Game is paused for card choosing, no updates
            cardChoosingState.update();
        } else if (gameState == GameState.PAUSED) {
            // Game is paused, no updates
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        enemiesManager.drawEnemies(g2);
        projectileManager.drawProjectiles(g2);
        statueManager.drawStatues(g2);
        if (gameState == GameState.CARD_CHOOSING) {
            cardChoosingState.draw(g2);
        }
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }
}
