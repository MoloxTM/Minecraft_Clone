package fr.math.minecraft.client.gui.buttons;

import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.meshs.ButtonMesh;
import fr.math.minecraft.client.visitor.ButtonActionVisitor;
import fr.math.minecraft.client.visitor.ButtonVisitor;

import static org.lwjgl.glfw.GLFW.*;

public abstract class BlockButton {

    private final String text;
    private final float x, y, z;
    private boolean hovered;

    public BlockButton(String text, float x, float y) {
        this(text, x, y, -10);
    }

    public BlockButton(String text, float x, float y, float z) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.z = z;
        this.hovered = false;
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

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean contains(double mouseX, double mouseY) {
        if (mouseX < 0 || mouseY < 0) {
            return false;
        }
        mouseY = GameConfiguration.WINDOW_HEIGHT - mouseY;
        return (
            x <= mouseX && mouseX <= x + ButtonMesh.BUTTON_WIDTH &&
            y <= mouseY && mouseY <= y + ButtonMesh.BUTTON_HEIGHT
        );
    }

    public abstract <T> T accept(ButtonVisitor<T> visitor);

    public void handleInputs(long window, double mouseX, double mouseY) {
        if (!this.contains(mouseX, mouseY)) {
            hovered = false;
            return;
        }

        hovered = true;
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            this.accept(new ButtonActionVisitor());
        }
    }

    public float getZ() {
        return z;
    }
}
