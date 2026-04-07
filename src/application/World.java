package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class World {

    private int width, height;
    private int[][] blocks;

    public static final int TILE_SIZE = 32;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new int[width][height];

        generateWorld();
    }

    private void generateWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if (y == height / 2) {
                    blocks[x][y] = 2; // relva
                } 
                else if (y > height / 2 && y < height - 3) {
                    blocks[x][y] = 1; // terra
                } 
                else if (y >= height - 3) {
                    blocks[x][y] = 3; // pedra
                } 
                else {
                    blocks[x][y] = 0; // ar
                }
            }
        }
    }    

    public void setBlock(int x, int y, int value) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            blocks[x][y] = value;
        }
    }
    public void render(GraphicsContext gc, double cameraX, double cameraY) {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	if (blocks[x][y] != 0) {

            	    double drawX = x * TILE_SIZE - cameraX;
            	    double drawY = y * TILE_SIZE - cameraY;

            	    switch (blocks[x][y]) {
            	        case 1:
            	            gc.drawImage(Block.DIRT, drawX, drawY, TILE_SIZE, TILE_SIZE);
            	            break;
            	        case 2:
            	            gc.drawImage(Block.GRASS, drawX, drawY, TILE_SIZE, TILE_SIZE);
            	            break;
            	        case 3:
            	            gc.drawImage(Block.STONE, drawX, drawY, TILE_SIZE, TILE_SIZE);
            	            break;
            	    }
            	}
            }
        }
    }
    
    public boolean isSolid(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return false;
        return blocks[x][y] == 1;
    }
}