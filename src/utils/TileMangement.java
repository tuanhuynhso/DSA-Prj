package utils;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import entity.Tile;
import main.GamePanel;

public class TileMangement {
    private GamePanel gp;

    protected Tile[] tile;
    protected int[][] mapTileNum;

    public TileMangement(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMap("/res/maps/map1.txt");
        loadTile();
    }

    private void loadTile() {
        tile = new Tile[2];
        tile[0] = new Tile(gp,0);
        tile[1] = new Tile(gp,1);
    }

    private void loadMap(String filename) {
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            
            if (is == null) {
                throw new IllegalArgumentException("Map file not found: " + filename);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int leftCol = Math.max(0, (gp.mainCharacter.getWorldX() - gp.screenWidth / 2) / gp.tileSize - 1);
        int rightCol = Math.min(gp.maxWorldCol - 1, (gp.mainCharacter.getWorldX() + gp.screenWidth / 2) / gp.tileSize + 1);
        int topRow = Math.max(0, (gp.mainCharacter.getWorldY() - gp.screenHeight / 2) / gp.tileSize - 1);
        int bottomRow = Math.min(gp.maxWorldRow - 1, (gp.mainCharacter.getWorldY() + gp.screenHeight / 2) / gp.tileSize + 1);

        for (int j = topRow; j <= bottomRow; j++) {
            for (int i = leftCol; i <= rightCol; i++) {
                int worldX = i * gp.tileSize;
                int worldY = j * gp.tileSize;
                int screenX = worldX - gp.mainCharacter.getWorldX() + gp.screenWidth / 2;
                int screenY = worldY - gp.mainCharacter.getWorldY() + gp.screenHeight / 2;

                tile[mapTileNum[i][j]].draw(g2d, screenX, screenY);
            }
        }
    }

}
