package utils;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import inGameEntity.Tile;

import java.io.InputStream;
import java.awt.image.BufferedImage;

import main.Constant;
import main.GamePanel;

public class TileMangement {
    private static TileMangement instance = null;

    private GamePanel gp;

    protected Tile[] tile;
    protected int[][] mapTileNum;
    BufferedImage mapCache;

    private TileMangement(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[Constant.maxWorldCol][Constant.maxWorldRow];
        loadMap("/res/maps/map1.txt");
        loadTile();
        buildMapCache();
    }

    public static TileMangement getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new TileMangement(gp);
        }
        return instance;
    }

    private void loadTile() {
        tile = new Tile[3];
        tile[0] = new Tile(0);
        tile[1] = new Tile(1);
        tile[2] = new Tile(2);
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

            while (row < Constant.maxWorldRow) {
                String line = br.readLine();
                while (col < Constant.maxWorldCol) {
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

    public void buildMapCache() {
        mapCache = new BufferedImage(Constant.maxWorldCol * Constant.tileSize, Constant.maxWorldRow * Constant.tileSize,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mapCache.createGraphics();

        for (int row = 0; row < Constant.maxWorldRow; row++) {
            for (int col = 0; col < Constant.maxWorldCol; col++) {
                int tileId = mapTileNum[col][row];
                if (tileId == 2) {
                    StatueManager.getInstance(gp).addStatue(new Point(col, row));
                }
                BufferedImage tileI = tile[tileId].getTileImage(); // tile image from sprite sheet
                if (tileId == 0) {
                    int subTileSize = tileI.getWidth() / 2;
                    int[][] positions = { { 0, 0 }, { subTileSize, 0 }, { 0, subTileSize },
                            { subTileSize, subTileSize } };
                    int idx = (int) (Math.random() * 4);
                    int subX = positions[idx][0];
                    int subY = positions[idx][1];
                    BufferedImage subImg = tileI.getSubimage(subX, subY, subTileSize, subTileSize);
                    g.drawImage(subImg, col * Constant.tileSize, row * Constant.tileSize, Constant.tileSize, Constant.tileSize, null);
                    continue;
                }
                g.drawImage(tileI, col * Constant.tileSize, row * Constant.tileSize, Constant.tileSize,
                        Constant.tileSize, null);
            }
        }

    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(mapCache, -gp.screenManagement.getScreenX(), -gp.screenManagement.getScreenY(), null);
    }

    public int getMapTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    public Tile getTile(int index) {
        return tile[index];
    }
}
