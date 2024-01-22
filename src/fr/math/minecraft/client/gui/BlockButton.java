package fr.math.minecraft.client.gui;

public class BlockButton {

    private final String text;
    private final float x, y;

    public BlockButton(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
