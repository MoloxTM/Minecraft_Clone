package fr.math.minecraft.client.fonts;

import org.joml.Vector2f;

public class CharInfo {

    private int sourceX, sourceY, width, height;
    private Vector2f[] textureCoords = new Vector2f[4];

    public CharInfo(int sourceX, int sourceY, int width, int height) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.width = width;
        this.height = height;
    }

    public int getSourceX() {
        return sourceX;
    }

    public void setSourceX(int sourceX) {
        this.sourceX = sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public void setSourceY(int sourceY) {
        this.sourceY = sourceY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void calculateTextureCoordinates(int fontWidth, int fontHeight) {
        float x0 = (float) sourceX / (float) fontWidth;
        float x1 = (float) (sourceX + width) / (float) fontWidth;
        float y0 = (float) sourceY / (float) fontHeight;
        float y1 = (float) (sourceY + height) / (float) fontHeight;

        textureCoords[0] = new Vector2f(x0, y0);
        textureCoords[1] = new Vector2f(x0, y1);
        textureCoords[2] = new Vector2f(x1, y0);
        textureCoords[3] = new Vector2f(x1, y1);
    }

    public Vector2f[] getTextureCoords() {
        return textureCoords;
    }
}
