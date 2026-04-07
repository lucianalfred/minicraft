package application;

public class Chunk {

    public static final int SIZE = 16;  

    private int[][] blocks;
    private int chunkX;
    private int height;

    public Chunk(int chunkX, int height) {
        this.chunkX = chunkX;
        this.height = height;
        blocks = new int[SIZE][height];

        generate(height);
    }

    private void generate(int height) {
        for (int x = 0; x < SIZE; x++) {
            // Coordenada X global do mundo
            int worldX = x + chunkX * SIZE;

            // Geração de terreno mais interessante
            int groundHeight = (int)(height - 15 
                + Math.sin(worldX * 0.1) * 4 
                + Math.sin(worldX * 0.03) * 6
                + Math.cos(worldX * 0.05) * 3);

            // Limita a altura
            groundHeight = Math.max(5, Math.min(height - 10, groundHeight));

            for (int y = 0; y < height; y++) {
                if (y == groundHeight) {
                    blocks[x][y] = 2; // Grama
                } 
                else if (y > groundHeight && y < groundHeight + 4) {
                    blocks[x][y] = 1; // Terra
                } 
                else if (y >= groundHeight + 4) {
                    blocks[x][y] = 3; // Pedra
                } 
                else {
                    blocks[x][y] = 0; // Ar
                }
            }
            
            // Adiciona árvores aleatórias
            if (Math.random() < 0.05 && groundHeight > 2) {
                addTree(x, groundHeight);
            }
        }
    }
    
    private void addTree(int x, int groundY) {
        // Tronco
        if (groundY - 1 >= 0) blocks[x][groundY - 1] = 1;
        if (groundY - 2 >= 0) blocks[x][groundY - 2] = 1;
        
        // Copa (simples)
        if (x > 0 && groundY - 3 >= 0) blocks[x-1][groundY - 3] = 2;
        if (groundY - 3 >= 0) blocks[x][groundY - 3] = 2;
        if (x < SIZE-1 && groundY - 3 >= 0) blocks[x+1][groundY - 3] = 2;
    }

    public int getBlock(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < height) {
            return blocks[x][y];
        }
        return 0;
    }

    public void setBlock(int x, int y, int value) {
        if (x >= 0 && x < SIZE && y >= 0 && y < height) {
            blocks[x][y] = value;
        }
    }
    
    public int getChunkX() {
        return chunkX;
    }
}