package fr.math.minecraft.client.fonts;

import org.joml.Vector2f;

public class CharInfo {

    private final int sourceX, sourceY, width, height;
    private final Vector2f[] textureCoords = new Vector2f[2];

    public CharInfo(int sourceX, int sourceY, int width, int height) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.width = width;
        this.height = height;
    }

    public int getSourceX() {
        return sourceX;
    }


    public int getSourceY() {
        return sourceY;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public void calculateTextureCoordinates(int fontWidth, int fontHeight) {
        float x0 = (float) sourceX / (float) fontWidth;
        float x1 = (float) (sourceX + width) / (float) fontWidth;
        float y0 = 1 - (float) (sourceY - height) / (float) fontHeight;
        float y1 = 1 - (float) sourceY / (float) fontHeight;

        textureCoords[0] = new Vector2f(x0, y1);
        textureCoords[1] = new Vector2f(x1, y0);
    }

    public Vector2f[] getTextureCoords() {
        return textureCoords;
    }
}
