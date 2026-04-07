package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {

    private Canvas canvas;
    private GraphicsContext gc;

    private World world;
    private Player player;

    private int cameraX = 0;  // Usar int em vez de double
    private int cameraY = 0;

    private int selectedBlock = 1;
    
    public Game(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        world = new World(100, 60);
        player = new Player(10, 10);

        // INPUT DO TECLADO
        scene.setOnKeyPressed(e -> {
            player.addKey(e.getCode());

            if (e.getCode().toString().equals("DIGIT1")) selectedBlock = 1;
            if (e.getCode().toString().equals("DIGIT2")) selectedBlock = 2;
            if (e.getCode().toString().equals("DIGIT3")) selectedBlock = 3;
        });
        
        scene.setOnKeyReleased(e -> {
            player.removeKey(e.getCode());
        });
        
        // INPUT DO MOUSE
        canvas.setOnMousePressed(e -> {
            int mouseX = (int) ((e.getX() + cameraX) / World.TILE_SIZE);
            int mouseY = (int) ((e.getY() + cameraY) / World.TILE_SIZE);

            if (mouseX >= 0 && mouseY >= 0 && 
                mouseX < world.getWidth() && mouseY < world.getHeight()) {
                
                if (e.isPrimaryButtonDown()) {
                    world.setBlock(mouseX, mouseY, 0);
                }

                if (e.isSecondaryButtonDown()) {
                    world.setBlock(mouseX, mouseY, selectedBlock);
                }
            }
        });
    }

    public void start() {
        new AnimationTimer() {
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }

    private void update() {
        player.update(world);
        
        // Câmera segue o player (em pixels inteiros)
        cameraX = (int)(player.getX() * World.TILE_SIZE - canvas.getWidth() / 2);
        cameraY = (int)(player.getY() * World.TILE_SIZE - canvas.getHeight() / 2);
        
        // Limita a câmera
        cameraX = Math.max(0, Math.min(cameraX, 
            world.getWidth() * World.TILE_SIZE - (int)canvas.getWidth()));
        cameraY = Math.max(0, Math.min(cameraY, 
            world.getHeight() * World.TILE_SIZE - (int)canvas.getHeight()));
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        world.render(gc, cameraX, cameraY);
        player.render(gc, cameraX, cameraY);
        
        // Informações na tela
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(14));
        gc.fillText("Selected block: " + getBlockName(selectedBlock), 10, 20);
        gc.fillText("Position (tiles): " + String.format("%.1f", player.getX()) + ", " + 
                   String.format("%.1f", player.getY()), 10, 40);
        gc.fillText("Position (pixels): " + (int)(player.getX() * World.TILE_SIZE) + ", " + 
                   (int)(player.getY() * World.TILE_SIZE), 10, 60);
    }
    
    private String getBlockName(int id) {
        switch(id) {
            case 1: return "Dirt";
            case 2: return "Grass";
            case 3: return "Stone";
            default: return "None";
        }
    }
}