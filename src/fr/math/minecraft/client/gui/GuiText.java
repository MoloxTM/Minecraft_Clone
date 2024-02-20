package fr.math.minecraft.client.gui;

import fr.math.minecraft.shared.GameConfiguration;

public class GuiText {

    private String text;
    private int rgb;
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

    public void setText(String text) {
        this.text = text;
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

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
