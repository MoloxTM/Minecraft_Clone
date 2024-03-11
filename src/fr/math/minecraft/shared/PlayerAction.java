package fr.math.minecraft.shared;

public enum PlayerAction {
    MINING(0, 0, 0, 10);

    private final int id, spriteX, spriteY, length;

    PlayerAction(int id, int spriteX, int spriteY, int length) {
        this.id = id;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
        this.length = length;
    }

    public int getSpriteX() {
        return spriteX;
    }

    public int getSpriteY() {
        return spriteY;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }
}
