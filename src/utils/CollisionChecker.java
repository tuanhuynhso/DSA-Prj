package utils;

import entity.Entity;
import main.GamePanel;

public class CollisionChecker {
    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        
        // Initialize collision states: 0 = no collision
        for (int i = 0; i < 4; i++) {
            entity.setCollisionOn(i, false);
        }
        int speedX = entity.getSpeedX();
        int speedY = entity.getSpeedY();

        int entityX = entity.getWorldX() + entity.getColGap();
        int entityY = entity.getWorldY() + entity.getRowGap();
        int entityXend = entityX + entity.getCollisionBox().width;
        int entityYend = entityY + entity.getCollisionBox().height;

        int entityLeftCol = entityX / gp.tileSize;
        int entityRightCol = entityXend / gp.tileSize;
        int entityTopRow = entityY / gp.tileSize;
        int entityBottomRow = entityYend / gp.tileSize;

        int tileNum1;
        int tileNum2;

        int nextLeftCol = (entityX - speedX) / gp.tileSize;
        int nextRightCol = (entityXend + speedX) / gp.tileSize;
        int nextTopRow = (entityY - speedY) / gp.tileSize;
        int nextBottomRow = (entityYend + speedY) / gp.tileSize;

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
            entity.setCollisionTile(1, nextRightCol);
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
            entity.setCollisionTile(3, nextBottomRow);
        }
    }
}
