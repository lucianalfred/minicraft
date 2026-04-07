package application;

import javafx.scene.image.Image;

public class Block {

    public static Image GRASS = new Image(
        Block.class.getResource("grass.jpg").toExternalForm()
    );

    public static Image DIRT = new Image(
        Block.class.getResource("dirt.jpg").toExternalForm()
    );

    public static Image STONE = new Image(
        Block.class.getResource("stone.png").toExternalForm()
    );
}