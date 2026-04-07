package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class Player {

    private double x, y;  // Posição em tiles (fracionário para movimento suave)
    private double vx = 0;
    private double vy = 0;

    private double speed = 0.15;  // Tiles por frame
    private double gravity = 0.02;
    private double jumpForce = -0.45;

    private boolean onGround = false;

    private Set<KeyCode> keys = new HashSet<>();

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
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

        // Movimento horizontal com colisão
        double newX = x + vx;
        if (!isColliding(world, newX, y)) {
            x = newX;
        } else {
            if (vx > 0) {
                x = Math.floor(x);
            } else if (vx < 0) {
                x = Math.ceil(x);
            }
            vx = 0;
        }

        // Movimento vertical com colisão
        double newY = y + vy;

        if (!isColliding(world, x, newY)) {
            y = newY;
            onGround = false;
        } else {
            if (vy > 0) {
                onGround = true;
                y = Math.floor(y);
            } else if (vy < 0) {
                y = Math.ceil(y);
            }
            vy = 0;
        }
    }

    private boolean isColliding(World world, double x, double y) {
        int leftTile = (int) Math.floor(x);
        int rightTile = (int) Math.floor(x + 0.9);
        int topTile = (int) Math.floor(y);
        int bottomTile = (int) Math.floor(y + 0.9);
        
        return world.isSolid(leftTile, topTile) ||
               world.isSolid(rightTile, topTile) ||
               world.isSolid(leftTile, bottomTile) ||
               world.isSolid(rightTile, bottomTile);
    }

    public void render(GraphicsContext gc, double cameraX, double cameraY) {
        double drawX = x * World.TILE_SIZE - cameraX;
        double drawY = y * World.TILE_SIZE - cameraY;
        
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
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public boolean isOnGround() { return onGround; }
}