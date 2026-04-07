package application;

import javafx.scene.image.Image;

public enum BlockType {
    AIR(0, null),
    DIRT(1, new Image(BlockType.class.getResource("dirt.png").toExternalForm())),
    GRASS(2, new Image(BlockType.class.getResource("grass.png").toExternalForm())),
    STONE(3, new Image(BlockType.class.getResource("stone.png").toExternalForm()));

    private final int id;
    private final Image texture;

    BlockType(int id, Image texture) {
        this.id = id;
        this.texture = texture;
    }

    public int getId() { return id; }
    public Image getTexture() { return texture; }

    public static BlockType fromId(int id) {
        for (BlockType b : values()) {
            if (b.id == id) return b;
        }
        return AIR;
    }
}