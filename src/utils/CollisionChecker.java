package utils;

import entity.Entity;
import main.Constant;
import main.GamePanel;

public class CollisionChecker {
    private final GamePanel gp;
    private static CollisionChecker instance = null;

    private CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public static CollisionChecker getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new CollisionChecker(gp);
        }
        return instance;
    }

    public void checkTile(Entity entity) {
        
        // Initialize collision states: 0 = no collision
        for (int i = 0; i < 4; i++) {
            entity.setCollisionOn(i, false);
        }
        int speed = entity.getSpeed();
        int entityXStart = entity.worldX - entity.getWidth() / 2 + entity.getColGap();
        int entityYStart = entity.worldY - entity.getHeight() / 2 + entity.getRowGap();
        int entityXend = entityXStart + entity.getCollisionBox().width - 1;
        int entityYend = entityYStart + entity.getCollisionBox().height - 1;

        int entityLeftCol = entityXStart / Constant.tileSize;
        int entityRightCol = entityXend / Constant.tileSize;
        int entityTopRow = entityYStart / Constant.tileSize;
        int entityBottomRow = entityYend / Constant.tileSize;

        int tileNum1;
        int tileNum2;

        int nextLeftCol = (entityXStart - speed) / Constant.tileSize;
        int nextRightCol = (entityXend + speed) / Constant.tileSize;
        int nextTopRow = (entityYStart - speed) / Constant.tileSize;
        int nextBottomRow = (entityYend + speed) / Constant.tileSize;

        if (nextLeftCol < 0) nextLeftCol = 0;
        if (nextRightCol >= Constant.maxWorldCol) nextRightCol = Constant.maxWorldCol - 1;
        if (nextTopRow < 0) nextTopRow = 0; 
        if (nextBottomRow >= Constant.maxWorldRow) nextBottomRow = Constant.maxWorldRow - 1;

        // Left collision
        tileNum1 = gp.tileM.mapTileNum[nextLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[nextLeftCol][entityBottomRow];
        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            entity.setCollisionOn(0, true); // Left collision
            entity.setCollisionTile( 0, nextLeftCol + 1);
        }
        // Right collision
        tileNum1 = gp.tileM.mapTileNum[nextRightCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[nextRightCol][entityBottomRow];
        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            entity.setCollisionOn(1, true); // Right collision
            entity.setCollisionTile(1, nextRightCol - 1);
        }
        // Up collision
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][nextTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][nextTopRow];
        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            entity.setCollisionOn(2, true); // Up collision
            entity.setCollisionTile(2, nextTopRow + 1);
        }
        // Down collision
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][nextBottomRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][nextBottomRow];
        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
            entity.setCollisionOn(3, true); // Down collision
            entity.setCollisionTile(3, nextBottomRow - 1);
        }
    }
}
