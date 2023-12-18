package fr.math.minecraft.client.player;

import fr.math.minecraft.client.packet.PlayerMovePacket;
import fr.math.minecraft.client.player.PlayerDirection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWWindowCloseCallback;

import java.nio.Buffer;
import java.nio.DoubleBuffer;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;


public class Player {

    private Vector3f position;
    private float yaw;
    private float pitch;
    private float speed;
    private boolean firstMouse;
    private float lastMouseX, lastMouseY;
    private final String name;
    private String uuid;

    public Player(String name) {
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = 0.05f;
        this.name = name;
        this.uuid = null;
    }

    public void handleInputs(long window) {
        DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, mouseX, mouseY);

        if (firstMouse) {
            lastMouseX = (float) mouseX.get(0);
            lastMouseY = (float) mouseY.get(0);
            firstMouse = false;
        }

        double mouseOffsetX = mouseX.get(0) - lastMouseX;
        double mouseOffsetY = mouseY.get(0) - lastMouseY;

        yaw += (float) mouseOffsetX * speed;
        if (yaw > 360.0f || yaw < -360.0f){
            yaw = 0.0f;
        }

        pitch -= (float) mouseOffsetY * speed;
        if (pitch > 90.0f){
            pitch = 89.0f;
        } else if (pitch < -90.0f){
            pitch = -89.0f;
        }

        PlayerMovePacket packet = null;

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
            packet = new PlayerMovePacket(this, PlayerDirection.FORWARD);
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
            packet = new PlayerMovePacket(this, PlayerDirection.LEFT);
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
            packet = new PlayerMovePacket(this, PlayerDirection.BACKWARD);
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
            packet = new PlayerMovePacket(this, PlayerDirection.RIGHT);
        }

        if (packet != null) {
            packet.send();
        }

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
