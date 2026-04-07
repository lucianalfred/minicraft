package application;

import javafx.scene.canvas.GraphicsContext;

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
            // Terreno mais interessante
            int groundHeight = (int)(height - 15 
                        + Math.sin(x * 0.1) * 3 
                        + Math.sin(x * 0.03) * 5);
            
            for (int y = 0; y < height; y++) {
                if (y > groundHeight) {
                    if (y == groundHeight + 1) {
                        blocks[x][y] = 2; // grama no topo
                    } else if (y < groundHeight + 4) {
                        blocks[x][y] = 1; // terra
                    } else {
                        blocks[x][y] = 3; // pedra
                    }
                } else {
                    blocks[x][y] = 0; // ar
                }
            }
        }
        
        // Adiciona algumas árvores
        for (int i = 0; i < 10; i++) {
            int treeX = 20 + (int)(Math.random() * (width - 40));
            int treeY = getGroundHeight(treeX) - 1;
            if (treeY > 2) {
                blocks[treeX][treeY] = 1; // tronco
                if (treeY - 1 >= 0) blocks[treeX][treeY - 1] = 1; // tronco parte 2
            }
        }
    }
    
    private int getGroundHeight(int x) {
        for (int y = 0; y < height; y++) {
            if (blocks[x][y] != 0) {
                return y;
            }
        }
        return height - 1;
    }
    
    public void setBlock(int x, int y, int value) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            blocks[x][y] = value;
        }
    }
    
    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        // Renderiza apenas o que está visível (otimização)
        int startX = Math.max(0, (int)(cameraX / TILE_SIZE));
        int endX = Math.min(width, (int)((cameraX + 800) / TILE_SIZE) + 2);
        int startY = Math.max(0, (int)(cameraY / TILE_SIZE));
        int endY = Math.min(height, (int)((cameraY + 600) / TILE_SIZE) + 2);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
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
        if (x < 0 || y < 0 || x >= width || y >= height) return true; // Fora do mundo é sólido
        return blocks[x][y] != 0; // Qualquer bloco que não seja ar é sólido
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}