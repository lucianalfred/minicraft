package application;

import javafx.scene.canvas.GraphicsContext;
import java.util.HashMap;
import java.util.Map;

public class World {

    private Map<Integer, Chunk> chunks = new HashMap<>();
    private int height = 60;  // Altura do mundo em blocos
    private int renderDistance = 8;  // Chunks renderizados em cada direção

    public static final int TILE_SIZE = 32;

    public World() {
        // O mundo começa vazio - chunks são gerados sob demanda
    }

    private Chunk getOrCreateChunk(int chunkX) {
        if (!chunks.containsKey(chunkX)) {
            chunks.put(chunkX, new Chunk(chunkX, height));
        }
        return chunks.get(chunkX);
    }
    
    private Chunk getChunk(int chunkX) {
        return chunks.get(chunkX);
    }

    public int getBlock(int worldX, int worldY) {
        if (worldY < 0 || worldY >= height) return 0;
        
        int chunkX = worldX / Chunk.SIZE;
        int localX = worldX % Chunk.SIZE;
        
        if (localX < 0) {
            chunkX--;
            localX += Chunk.SIZE;
        }
        
        Chunk chunk = getChunk(chunkX);
        if (chunk == null) return 0;
        
        return chunk.getBlock(localX, worldY);
    }

    public void setBlock(int worldX, int worldY, int value) {
        if (worldY < 0 || worldY >= height) return;
        
        int chunkX = worldX / Chunk.SIZE;
        int localX = worldX % Chunk.SIZE;
        
        if (localX < 0) {
            chunkX--;
            localX += Chunk.SIZE;
        }
        
        Chunk chunk = getOrCreateChunk(chunkX);
        chunk.setBlock(localX, worldY, value);
    }
    
    public boolean isSolid(int worldX, int worldY) {
        int block = getBlock(worldX, worldY);
        return block != 0;  // Ar = 0, qualquer outro bloco é sólido
    }

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        // Calcula quais chunks estão visíveis
        int startChunk = (int)(cameraX / (Chunk.SIZE * TILE_SIZE)) - 1;
        int endChunk = startChunk + renderDistance;
        
        // Limita para não gerar chunks infinitos
        startChunk = Math.max(-100, startChunk);
        endChunk = Math.min(100, endChunk);
        
        for (int cx = startChunk; cx <= endChunk; cx++) {
            Chunk chunk = getOrCreateChunk(cx);
            
            int chunkWorldX = cx * Chunk.SIZE * TILE_SIZE;
            
            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < height; y++) {
                    int block = chunk.getBlock(x, y);
                    if (block == 0) continue;
                    
                    double worldX = chunkWorldX + x * TILE_SIZE;
                    double worldY = y * TILE_SIZE;
                    
                    double drawX = worldX - cameraX;
                    double drawY = worldY - cameraY;
                    
                    // Só desenha se estiver dentro da tela (otimização)
                    if (drawX + TILE_SIZE < 0 || drawX > 800 || 
                        drawY + TILE_SIZE < 0 || drawY > 600) {
                        continue;
                    }
                    
                    switch (block) {
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
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        // Retorna "infinito" conceitualmente
        return Integer.MAX_VALUE;
    }
    
    // Opcional: Limpar chunks distantes para economizar memória
    public void unloadDistantChunks(int playerChunkX, int distance) {
        chunks.entrySet().removeIf(entry -> 
            Math.abs(entry.getKey() - playerChunkX) > distance
        );
    }
}