package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class Player {

    private int pixelX, pixelY;  // Posição em PIXELS (inteiro)
    private double vx = 0;
    private double vy = 0;

    private double speed = 3.0;  // pixels por frame
    private double gravity = 0.5;
    private double jumpForce = -15;

    private boolean onGround = false;

    private Set<KeyCode> keys = new HashSet<>();

    public Player(double x, double y) {
        // Converte de tiles para pixels
        this.pixelX = (int)(x * World.TILE_SIZE);
        this.pixelY = (int)(y * World.TILE_SIZE);
    }

    public void addKey(KeyCode key) {
        keys.add(key);
    }

    public void removeKey(KeyCode key) {
        keys.remove(key);
    }

    public void update(World world) {
        // Movimento horizontal
        vx = 0;
        if (keys.contains(KeyCode.A) || keys.contains(KeyCode.LEFT)) {
            vx = -speed;
        }
        if (keys.contains(KeyCode.D) || keys.contains(KeyCode.RIGHT)) {
            vx = speed;
        }

        // Pulo
        if ((keys.contains(KeyCode.W) || keys.contains(KeyCode.UP) || keys.contains(KeyCode.SPACE)) 
            && onGround) {
            vy = jumpForce;
            onGround = false;
        }

        // Gravidade
        vy += gravity;

        // Movimento horizontal com colisão (em pixels)
        int newPixelX = pixelX + (int)vx;
        if (!isColliding(world, newPixelX, pixelY)) {
            pixelX = newPixelX;
        } else {
            vx = 0;
        }

        // Movimento vertical com colisão (em pixels)
        int newPixelY = pixelY + (int)vy;

        if (!isColliding(world, pixelX, newPixelY)) {
            pixelY = newPixelY;
            onGround = false;
        } else {
            if (vy > 0) {
                onGround = true;
            }
            vy = 0;
        }
    }

    private boolean isColliding(World world, int pixelX, int pixelY) {
        // Converte pixel para tile
        int tileX = pixelX / World.TILE_SIZE;
        int tileY = pixelY / World.TILE_SIZE;
        
        // Verifica os 4 cantos do player
        int leftTile = tileX;
        int rightTile = (pixelX + World.TILE_SIZE - 1) / World.TILE_SIZE;
        int topTile = tileY;
        int bottomTile = (pixelY + World.TILE_SIZE - 1) / World.TILE_SIZE;
        
        return world.isSolid(leftTile, topTile) ||
               world.isSolid(rightTile, topTile) ||
               world.isSolid(leftTile, bottomTile) ||
               world.isSolid(rightTile, bottomTile);
    }

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        // Desenha em posição inteira de pixel
        double drawX = pixelX - cameraX;
        double drawY = pixelY - cameraY;
        
        gc.setFill(Color.BLUE);
        gc.fillRect(drawX, drawY, World.TILE_SIZE, World.TILE_SIZE);
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(drawX, drawY, World.TILE_SIZE, World.TILE_SIZE);
        
        // Olhos
        gc.setFill(Color.WHITE);
        gc.fillOval(drawX + 8, drawY + 8, 6, 6);
        gc.fillOval(drawX + 20, drawY + 8, 6, 6);
        gc.setFill(Color.BLACK);
        gc.fillOval(drawX + 9, drawY + 9, 3, 3);
        gc.fillOval(drawX + 21, drawY + 9, 3, 3);
    }
    
    // GETTERS (retornam em tiles para compatibilidade)
    public double getX() { 
        return pixelX / (double)World.TILE_SIZE; 
    }
    
    public double getY() { 
        return pixelY / (double)World.TILE_SIZE; 
    }
    
    public double getVx() {  
        return vx; 
    }
    
    public double getVy() { 
        return vy; 
    }
    
    public boolean isOnGround() { 
        return onGround; 
    }
}