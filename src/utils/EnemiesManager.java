package utils;

import main.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import entity.Crep;
import entity.Entity;
import inGameEntity.enemies.BossManticore;
import inGameEntity.enemies.ElderLich;
import inGameEntity.enemies.MiasmaMage;
import inGameEntity.enemies.Warbeast;
import main.Constant;

public class EnemiesManager {
    private static EnemiesManager instance = null;
    private Random random = new Random();

    public static EnemiesManager getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new EnemiesManager(gp);
        }
        return instance;
    }

    private ArrayList<Entity> enemies = new ArrayList<>();

    private EnemiesManager(GamePanel gp) {
        // Initialize enemies
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(Constant.maxWorldCol * Constant.tileSize);
            int y = random.nextInt(Constant.maxWorldRow * Constant.tileSize);
            enemies.add(new Crep(gp, x, y));
        }
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(Constant.maxWorldCol * Constant.tileSize);
            int y = random.nextInt(Constant.maxWorldRow * Constant.tileSize);
            enemies.add(new MiasmaMage(gp, x, y));
        }
        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(Constant.maxWorldCol * Constant.tileSize);
            int y = random.nextInt(Constant.maxWorldRow * Constant.tileSize);
            enemies.add(new Warbeast(gp, x, y));
        }
        // Add Bosses
        enemies.add(new ElderLich(gp, 1000, 1000));
        enemies.add(new BossManticore(gp, 1500, 1500));
    }

    public void enemiesGrowth() {
        for (Entity enemy : enemies) {
            enemy.growth();
        }
    }

    public void updateEnemies() {
        for (Entity enemy : enemies) {
            if (!enemy.isInFrame() && !enemy.isAlive()) {
                if (!enemy.isAlive()) {
                    enemy.worldX = random.nextInt(Constant.maxWorldCol * Constant.tileSize);
                    enemy.worldY = random.nextInt(Constant.maxWorldRow * Constant.tileSize);
                    enemy.revive();
                    continue;
                }
                continue;
            }
            if (!enemy.isInFrame()) {
                continue;
            }
            enemy.update();
        }
    }

    public void drawEnemies(Graphics2D g2) {
        for (Entity enemy : enemies) {
            if (!enemy.isAlive() || !enemy.isInFrame()) {
                continue;
            }
            enemy.draw(g2);
        }
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }
}
