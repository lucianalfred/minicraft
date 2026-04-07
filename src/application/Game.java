package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Canvas canvas;
    private GraphicsContext gc;

    private World world;
    private Player player;

    private double cameraX = 0;
    private double cameraY = 0;

    private int selectedBlock = 1;
    
    public Game(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        world = new World(50, 50);
        player = new Player(5, 5);

        
        // INPUT
        scene.setOnKeyPressed(e -> {
            player.addKey(e.getCode());

            if (e.getCode().toString().equals("DIGIT1")) selectedBlock = 1;
            if (e.getCode().toString().equals("DIGIT2")) selectedBlock = 2;
            if (e.getCode().toString().equals("DIGIT3")) selectedBlock = 3;
        });
        
        canvas.setOnMousePressed(e -> {

            int mouseX = (int) ((e.getX() + cameraX) / World.TILE_SIZE);
            int mouseY = (int) ((e.getY() + cameraY) / World.TILE_SIZE);

            if (e.isPrimaryButtonDown()) {
                world.setBlock(mouseX, mouseY, 0);
            }

            if (e.isSecondaryButtonDown()) {
                world.setBlock(mouseX, mouseY, selectedBlock);
            }
        });
        
     // INPUT DO MOUSE
        canvas.setOnMousePressed(e -> {

            int mouseX = (int) ((e.getX() + cameraX) / World.TILE_SIZE);
            int mouseY = (int) ((e.getY() + cameraY) / World.TILE_SIZE);

            if (e.isPrimaryButtonDown()) {
                // botão esquerdo → remover bloco
                world.setBlock(mouseX, mouseY, 0);
            }

            if (e.isSecondaryButtonDown()) {
                // botão direito → colocar bloco
                world.setBlock(mouseX, mouseY, 1);
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
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // câmera segue o player
        cameraX = player.getX() * World.TILE_SIZE - canvas.getWidth() / 2;
        cameraY = player.getY() * World.TILE_SIZE - canvas.getHeight() / 2;

        world.render(gc, cameraX, cameraY);
        player.render(gc, cameraX, cameraY);
    }
}