package fr.math.minecraft.client.entity;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.animations.PlayerWalkAnimation;
import fr.math.minecraft.client.events.EventListener;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Player {


    public final static float WIDTH = .75f;
    public final static float HEIGHT = 1.75f;
    public final static float DEPTH = WIDTH;
    private Vector3f position;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private boolean firstMouse;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking;
    private boolean debugKeyPressed;

    private float lastMouseX, lastMouseY;
    private String name;
    private String uuid;
    private final ArrayList<Animation> animations;
    private NametagMesh nametagMesh;
    private BufferedImage skin;
    private float sensitivity;
    private final static Logger logger = LoggerUtility.getClientLogger(Player.class, LogType.TXT);
    private final List<EventListener> eventListeners;

    public Player(String name) {
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.bodyYaw = 0.0f;
        this.pitch = 0.0f;
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = 0.2f;
        this.sensitivity = 0.1f;
        this.name = name;
        this.uuid = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.debugKeyPressed = false;
        this.sneaking = false;
        this.flying = false;
        this.animations = new ArrayList<>();
        this.nametagMesh = new NametagMesh(name);
        this.skin = null;
        this.eventListeners = new ArrayList<>();
        this.initAnimations();
    }

    private void initAnimations() {
        animations.add(new PlayerWalkAnimation(this));
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

        yaw += (float) mouseOffsetX * sensitivity;
        if (yaw > 360.0f || yaw < -360.0f){
            yaw = 0.0f;
        }

        pitch -= (float) mouseOffsetY * sensitivity;
        if (pitch > 90.0f){
            pitch = 89.0f;
        } else if (pitch < -90.0f){
            pitch = -89.0f;
        }

        this.resetMoving();

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            movingForward = true;
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            movingLeft = true;
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            movingBackward = true;
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            movingRight = true;
        }

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            flying = true;
        }

        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            sneaking = true;
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_PRESS) {
            if (!debugKeyPressed) {
                Game.getInstance().setDebugging(!Game.getInstance().isDebugging());
                debugKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_RELEASE) {
            debugKeyPressed = false;
        }

        if (movingLeft || movingRight || movingForward || movingBackward || sneaking || flying) {
            this.notifyEvent(new PlayerMoveEvent(this));
        }

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    private void resetMoving() {
        movingLeft = false;
        movingRight = false;
        movingForward = false;
        movingBackward = false;
        flying = false;
        sneaking = false;
    }

    public void update() {
        for (Animation animation : animations) {
            animation.update();
        }
    }

    public boolean isMoving() {
        return movingLeft || movingRight || movingForward || movingBackward || sneaking || flying;
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

    public ArrayList<Animation> getAnimations() {
        return animations;
    }

    public NametagMesh getNametagMesh() {
        return nametagMesh;
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public void setBodyYaw(float bodyYaw) {
        this.bodyYaw = bodyYaw;
    }

    public void notifyEvent(PlayerMoveEvent event) {
        for (EventListener eventListener : eventListeners) {
            eventListener.onPlayerMove(event);
        }
    }

    public void addEventListener(EventListener event) {
        eventListeners.add(event);
    }

    public void removeEventListener(EventListener event) {
        eventListeners.remove(event);
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isSneaking() {
        return sneaking;
    }
}
