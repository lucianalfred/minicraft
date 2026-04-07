package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class Player {

    private double x, y;
    private double vx = 0;
    private double vy = 0;

    private double speed = 0.2;
    private double gravity = 0.02;
    private double jumpForce = -0.5;

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

        // movimento horizontal
        vx = 0;
        if (keys.contains(KeyCode.A)) {
            vx = -speed;
        }
        if (keys.contains(KeyCode.D)) {
            vx = speed;
        }

        // pulo
        if (keys.contains(KeyCode.W) && onGround) {
            vy = jumpForce;
            onGround = false;
        }

        // gravidade
        vy += gravity;

        // movimento horizontal com colisão
        double newX = x + vx;
        if (!isColliding(world, newX, y)) {
            x = newX;
        }

        // movimento vertical com colisão
        double newY = y + vy;

        if (!isColliding(world, x, newY)) {
            y = newY;
            onGround = false;
        } else {
            // colisão vertical
            if (vy > 0) {
                onGround = true;
            }
            vy = 0;
        }
    }

    private boolean isColliding(World world, double x, double y) {
        int tileX = (int) x;
        int tileY = (int) y;
        return world.isSolid(tileX, tileY);
    }

    public void render(GraphicsContext gc, double cameraX, double cameraY) {

        gc.setFill(Color.BLUE);
        gc.fillRect(
            x * World.TILE_SIZE - cameraX,
            y * World.TILE_SIZE - cameraY,
            World.TILE_SIZE,
            World.TILE_SIZE
        );
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}