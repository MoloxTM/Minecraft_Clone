package fr.math.minecraft.client.gui;

import fr.math.minecraft.client.GameConfiguration;

public class GuiText {

    private final String text;
    private final int rgb;
    private float x, y, z, rotateAngle, scale;

    public GuiText(String text, float x, float y, int rgb) {
        this(text, x, y, -10, rgb, 0.0f);
    }

    public GuiText(String text, float x, float y, float z, int rgb) {
        this(text, x, y, z, rgb, 0.0f);
    }

    public GuiText(String text, float x, float y, float z, int rgb, float rotateAngle) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rgb = rgb;
        this.rotateAngle = rotateAngle;
        this.scale = GameConfiguration.DEFAULT_SCALE;
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

    public float getZ() {
        return z;
    }

    public int getRgb() {
        return rgb;
    }

    public float getRotateAngle() {
        return rotateAngle;
    }

    public float getScale() {
        return scale;
    }

    public void rotate(float rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public void scale(float scale) {
        this.scale = scale;
    }
}
