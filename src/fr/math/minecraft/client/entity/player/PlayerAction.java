package fr.math.minecraft.client.entity.player;

public enum PlayerAction {
    MINING(0, 0, 10);

    private final int spriteX, spriteY, length;

    PlayerAction(int spriteX, int spriteY, int length) {
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
}
