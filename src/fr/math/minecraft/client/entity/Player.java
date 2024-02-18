package fr.math.minecraft.client.entity;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.animations.PlayerWalkAnimation;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.client.world.Material;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.shared.network.GameMode;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;
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
    private Vector3f lastTickPosition;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private boolean firstMouse;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking;
    private boolean debugKeyPressed;
    private float lastTickYaw, lastTickPitch;

    private float lastMouseX, lastMouseY;
    private String name;
    private String uuid;
    private final ArrayList<Animation> animations;
    private NametagMesh nametagMesh;
    private BufferedImage skin;
    private float sensitivity;
    private final static Logger logger = LoggerUtility.getClientLogger(Player.class, LogType.TXT);
    private final List<EventListener> eventListeners;
    private Vector3i lastTickInput;
    private final Vector3f velocity;
    private final Vector3f gravity;
    private float Vmax;
    private int ping;
    private final List<PlayerInputData> inputs;
    private final Hitbox hitbox;
    private GameMode gameMode;

    public Player(String name) {
        this.position = new Vector3f(0.0f, 200.0f, 0.0f);
        this.gravity = new Vector3f(0, -0.001f, 0);
        this.lastTickPosition = new Vector3f(0, 0, 0);
        this.lastTickInput = new Vector3i(0, 0, 0);
        this.velocity = new Vector3f();
        this.inputs = new ArrayList<>();
        this.yaw = 0.0f;
        this.Vmax = 1.0f;
        this.bodyYaw = 0.0f;
        this.lastTickYaw = 0.0f;
        this.lastTickPitch = 0.0f;
        this.pitch = 0.0f;
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = 0.1f;
        this.ping = 0;
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
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 0.9f, 0.25f));
        this.animations = new ArrayList<>();
        this.nametagMesh = new NametagMesh(name);
        this.skin = null;
        this.eventListeners = new ArrayList<>();
        this.gameMode = GameMode.CREATIVE;
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

    public void resetMoving() {
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

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public void handleCollisions(Vector3f velocity) {
        Game game = Game.getInstance();
        World world = game.getWorld();
        for (float worldX = position.x - hitbox.getWidth() ; worldX < position.x + hitbox.getWidth() ; worldX++) {
            for (float worldY = position.y - hitbox.getHeight() ; worldY < position.y + hitbox.getHeight() ; worldY++) {
                for (float worldZ = position.z - hitbox.getDepth() ; worldZ < position.z + hitbox.getDepth() ; worldZ++) {

                    byte block = world.getBlockAt((int) worldX, (int) worldY, (int) worldZ);
                    Material material = Material.getMaterialById(block);

                    if (material != null && !material.isSolid()) {
                        continue;
                    }

                    if (velocity.x > 0) {
                        position.x = worldX - hitbox.getWidth();
                    } else if(velocity.x<0) {
                        position.x = worldX + hitbox.getWidth();
                    }

                    if (velocity.y > 0) {
                        position.y = worldY - hitbox.getHeight();
                        this.velocity.y=0;
                    } else if(velocity.y<0) {
                        position.y = worldY + hitbox.getHeight() +1;
                        this.velocity.y=0;
                    }

                    if (velocity.z > 0) {
                        position.z = worldZ - hitbox.getDepth();
                    } else if(velocity.z<0) {
                        position.z = worldZ + hitbox.getDepth();
                    }
                }
            }
        }
    }

    public void updatePosition() {

        Vector3f front = new Vector3f();
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f acceleration = new Vector3f(0,0,0);

        //velocity.add(gravity);

        if (movingForward) {
            acceleration.add(front);
        }

        if (movingBackward) {
            acceleration.sub(front);
        }

        if (movingLeft) {
            acceleration.sub(right);
        }

        if (movingRight) {
            acceleration.add(right);
        }

        if (flying) {
            position.add(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (sneaking) {
            position.sub(new Vector3f(0.0f, .1f, 0.0f));
        }

        velocity.add(acceleration.mul(speed));

        if (velocity.length()>Vmax) {
            velocity.normalize().mul(Vmax);
        }

        this.handleCollisions(new Vector3f(position.x, 0, 0));

        velocity.x *= .95f;
        velocity.z *= .95f;

        position.x += velocity.x;
        handleCollisions(new Vector3f(position.x,0,0));

        position.y += velocity.y;
        handleCollisions(new Vector3f(0,position.y,0));

        position.z += velocity.z;
        handleCollisions(new Vector3f(0,0,position.z));

        PlayerInputData inputData = new PlayerInputData(movingLeft, movingRight, movingForward, movingBackward, flying, sneaking, yaw, pitch);
        inputs.add(inputData);
    }

    public Vector3f getLastTickPosition() {
        return lastTickPosition;
    }

    public void setLastTickPosition(Vector3f lastTickPosition) {
        this.lastTickPosition = lastTickPosition;
    }

    public Vector3i getLastTickInput() {
        return lastTickInput;
    }

    public float getLastTickPitch() {
        return lastTickPitch;
    }

    public float getLastTickYaw() {
        return lastTickYaw;
    }

    public void setLastTickInput(Vector3i lastTickInput) {
        this.lastTickInput = lastTickInput;
    }

    public void setLastTickYaw(float lastTickYaw) {
        this.lastTickYaw = lastTickYaw;
    }

    public void setLastTickPitch(float lastTickPitch) {
        this.lastTickPitch = lastTickPitch;
    }

    public List<PlayerInputData> getInputs() {
        return inputs;
    }
}
