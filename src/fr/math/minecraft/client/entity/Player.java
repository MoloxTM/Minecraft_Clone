package fr.math.minecraft.client.entity;

import fr.math.minecraft.client.packet.PlayerMovePacket;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;


public class Player {


    public final static float WIDTH = .75f;
    public final static float HEIGHT = 1.75f;
    public final static float DEPTH = WIDTH;
    private final static float HAND_WALKING_ANIMATION_SPEED = .5f;
    private final static float MAX_HAND_ROTATION_ANGLE = 30.0f;
    private Vector3f position;
    private float yaw;
    private float pitch;
    private float speed;
    private float handRotation;
    private String handRotationState;
    private boolean firstMouse;
    public boolean movingLeft, movingRight, movingForward, movingBackward;
    private float lastMouseX, lastMouseY;
    private String name;
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
        this.handRotation = 0.0f;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.handRotationState = "FORWARD";
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

        PlayerMovePacket packet = new PlayerMovePacket(this);

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            movingForward = true;
            packet.setMovingForward(true);
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            movingLeft = true;
            packet.setMovingLeft(true);
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            movingBackward = true;
            packet.setMovingBackward(true);
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            movingRight = true;
            packet.setMovingRight(true);
        }

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            packet.setFlying(true);
        }

        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            packet.setSneaking(true);
        }

        packet.send();

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    private void handleHandRotation() {
        if (handRotationState.equals("FORWARD")) {
            handRotation += HAND_WALKING_ANIMATION_SPEED;

            if (handRotation > MAX_HAND_ROTATION_ANGLE)
                handRotationState = "BACKWARD";
        } else {
            handRotation -= HAND_WALKING_ANIMATION_SPEED;

            if (handRotation < -MAX_HAND_ROTATION_ANGLE)
                handRotationState = "FORWARD";
        }
    }

    public void update() {
        if (!this.isMoving()) {
            handRotation *= 0.95f;
        } else {
            this.handleHandRotation();
        }
    }

    public boolean isMoving() {
        return movingLeft || movingRight || movingForward || movingBackward;
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

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getHandRotation() {
        return this.handRotation;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
    }
}
