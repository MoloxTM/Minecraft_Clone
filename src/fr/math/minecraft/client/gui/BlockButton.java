package fr.math.minecraft.client.gui;

public class BlockButton {

    private final String text;
    private final int x, y;

    public BlockButton(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
