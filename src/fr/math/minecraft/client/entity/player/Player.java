package fr.math.minecraft.client.entity.player;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.animations.PlayerHandAnimation;
import fr.math.minecraft.client.animations.PlayerWalkAnimation;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.shared.inventory.Hotbar;
import fr.math.minecraft.shared.inventory.PlayerInventory;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.network.GameMode;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Player {

    public final static float WIDTH = .75f;
    public final static float HEIGHT = 1.75f;
    public final static float DEPTH = WIDTH;
    private Vector3f position;
    private final Hotbar hotbar;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private boolean firstMouse;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking, canJump, canBreakBlock, jumping, sprinting;
    private boolean movingMouse;
    private boolean placingBlock, breakingBlock;
    private boolean debugKeyPressed, occlusionKeyPressed, interpolationKeyPressed, inventoryKeyPressed;
    private float lastMouseX, lastMouseY;
    private String name;
    private String uuid;
    private final ArrayList<Animation> animations;
    private NametagMesh nametagMesh;
    private BufferedImage skin;
    private float sensitivity;
    private final static Logger logger = LoggerUtility.getClientLogger(Player.class, LogType.TXT);
    private final List<EventListener> eventListeners;
    private final Vector3f velocity;
    private final Vector3f gravity;
    private float maxSpeed, maxFall;
    private int ping;
    private final List<PlayerInputData> inputs;
    private final Set<Coordinates> receivedChunks;
    private final Hitbox hitbox;
    private GameMode gameMode;
    private Vector3f lastPosition;
    private String skinPath;
    private final PlayerHand hand;
    private EntityUpdate lastUpdate;
    private int breakBlockCooldown;
    private Ray attackRay, buildRay;
    private ArrayList<Vector3i> aimedBlocks;
    private final PlayerInventory inventory;
    private final float health;
    private final float maxHealth;

    public Player(String name) {
        this.position = new Vector3f(0.0f, 100.0f, 0.0f);
        this.lastPosition = new Vector3f(0, 0, 0);
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.velocity = new Vector3f();
        this.receivedChunks = new HashSet<>();
        this.inputs = new ArrayList<>();
        this.hand = new PlayerHand();
        this.inventory = new PlayerInventory();
        this.yaw = 0.0f;
        this.bodyYaw = 0.0f;
        this.pitch = 0.0f;
        this.lastUpdate = new EntityUpdate(new Vector3f(position), yaw, pitch, bodyYaw);
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = GameConfiguration.DEFAULT_SPEED;
        this.maxSpeed = 0.03f;
        this.maxFall = 0.03f;
        this.health = 20.0f;
        this.maxHealth = 20.0f;
        this.ping = 0;
        this.sensitivity = 0.1f;
        this.name = name;
        this.uuid = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.debugKeyPressed = false;
        this.occlusionKeyPressed = false;
        this.interpolationKeyPressed = false;
        this.inventoryKeyPressed = false;
        this.movingMouse = true;
        this.sneaking = false;
        this.sprinting = false;
        this.flying = false;
        this.canJump = false;
        this.canBreakBlock = true;
        this.jumping = false;
        this.placingBlock = false;
        this.breakingBlock = false;
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 1.0f, 0.25f));
        this.animations = new ArrayList<>();
        this.nametagMesh = new NametagMesh(name);
        this.hotbar = new Hotbar();
        this.skin = null;
        this.skinPath = "res/textures/skin.png";
        this.eventListeners = new ArrayList<>();
        this.gameMode = GameMode.SURVIVAL;
        this.attackRay = new Ray(GameConfiguration.ATTACK_REACH);
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.aimedBlocks = new ArrayList<>();
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

        if (mouseOffsetX != 0 || mouseOffsetY != 0) {
            movingMouse = true;
        }

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

        if(glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS) {
            if(!sprinting) {
                sprinting = true;
            } else {
                sprinting = false;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            switch (gameMode) {
                case CREATIVE :
                    flying = true;
                    break;
                case SURVIVAL :
                    jumping = true;
                    break;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            sneaking = true;
        }

        if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
            if (!occlusionKeyPressed) {
                GameConfiguration gameConfiguration = Game.getInstance().getGameConfiguration();
                gameConfiguration.setOcclusionEnabled(!gameConfiguration.isOcclusionEnabled());
                occlusionKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_F1) == GLFW_PRESS) {
            if (!interpolationKeyPressed) {
                GameConfiguration gameConfiguration = Game.getInstance().getGameConfiguration();
                gameConfiguration.setEntityInterpolation(!gameConfiguration.isEntityInterpolationEnabled());
                interpolationKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_PRESS) {
            if (!debugKeyPressed) {
                GameConfiguration gameConfiguration = Game.getInstance().getGameConfiguration();
                gameConfiguration.setDebugging(!gameConfiguration.isDebugging());
                debugKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS) {
            if (!inventoryKeyPressed) {
                inventory.setOpen(!inventory.isOpen());
                inventoryKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS) {
            hotbar.setCurrentSlot(0);
        }

        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS) {
            hotbar.setCurrentSlot(1);
        }

        if (glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS) {
            hotbar.setCurrentSlot(2);
        }

        if (glfwGetKey(window, GLFW_KEY_4) == GLFW_PRESS) {
            hotbar.setCurrentSlot(3);
        }

        if (glfwGetKey(window, GLFW_KEY_5) == GLFW_PRESS) {
            hotbar.setCurrentSlot(4);
        }

        if (glfwGetKey(window, GLFW_KEY_6) == GLFW_PRESS) {
            hotbar.setCurrentSlot(5);
        }

        if (glfwGetKey(window, GLFW_KEY_7) == GLFW_PRESS) {
            hotbar.setCurrentSlot(6);
        }

        if (glfwGetKey(window, GLFW_KEY_8) == GLFW_PRESS) {
            hotbar.setCurrentSlot(7);
        }

        if (glfwGetKey(window, GLFW_KEY_9) == GLFW_PRESS) {
            hotbar.setCurrentSlot(8);
        }

        if (glfwGetKey(window, GLFW_KEY_E) == GLFW_RELEASE) {
            inventoryKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_RELEASE) {
            debugKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_RELEASE) {
            occlusionKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_F1) == GLFW_RELEASE) {
            interpolationKeyPressed = false;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            breakingBlock = true;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
            breakingBlock = false;
            canBreakBlock = true;
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
        jumping = false;
        movingMouse = false;
        //breakingBlock = false;
        //placingBlock = false;
    }

    public void updateAnimations() {
        for (Animation animation : animations) {
            animation.update();
        }
    }

    public void update() {
        this.updateAnimations();
        GameConfiguration gameConfiguration = Game.getInstance().getGameConfiguration();

        if (gameConfiguration.isEntityInterpolationEnabled()) {
            position.x = Math.lerp(position.x, lastUpdate.getPosition().x, 0.1f);
            position.y = Math.lerp(position.y, lastUpdate.getPosition().y, 0.1f);
            position.z = Math.lerp(position.z, lastUpdate.getPosition().z, 0.1f);
            yaw = Math.lerp(yaw, lastUpdate.getYaw(), 0.1f);
            pitch = Math.lerp(pitch, lastUpdate.getPitch(), 0.1f);
            bodyYaw = Math.lerp(bodyYaw, lastUpdate.getBodyYaw(), 0.1f);
        }
    }

    private void handleJump() {
        if (canJump) {
            maxFall = 0.5f;
            canJump = false;
        }
    }

    public void handleCollisions(World world, Vector3f velocity) {

        int minX = (int) Math.floor(position.x - hitbox.getWidth());
        int maxX = (int) Math.ceil(position.x + hitbox.getWidth());
        int minY = (int) Math.floor(position.y - hitbox.getHeight());
        int maxY = (int) Math.ceil(position.y + hitbox.getHeight());
        int minZ = (int) Math.floor(position.z - hitbox.getDepth());
        int maxZ = (int) Math.ceil(position.z + hitbox.getDepth());

        for (int worldX = minX; worldX < maxX; worldX++) {
            for (int worldY = minY; worldY < maxY; worldY++) {
                for (int worldZ = minZ; worldZ < maxZ; worldZ++) {

                    byte block = world.getBlockAt(worldX, worldY, worldZ);
                    Material material = Material.getMaterialById(block);

                    if (!material.isSolid()) {
                        continue;
                    }

                    if (velocity.x > 0) {
                        position.x = worldX - hitbox.getWidth();
                    } else if (velocity.x < 0) {
                        position.x = worldX + hitbox.getWidth() + 1;
                    }

                    if (velocity.y > 0) {
                        position.y = worldY - hitbox.getHeight();
                        this.velocity.y = 0;
                    } else if (velocity.y < 0) {
                        maxFall = 0.03f;
                        canJump = true;
                        position.y = worldY + hitbox.getHeight() + 1;
                        this.velocity.y = 0;
                    }

                    if (velocity.z > 0) {
                        position.z = worldZ - hitbox.getDepth();
                    } else if (velocity.z < 0) {
                        position.z = worldZ + hitbox.getDepth() + 1;
                    }
                }
            }
        }
    }

    public void updatePosition(World world) {

        Vector3f front = new Vector3f();
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f acceleration = new Vector3f(0, 0, 0);

        velocity.add(gravity);

        if (sprinting) {
            this.setSpeed(GameConfiguration.SPRINT_SPEED);
        } else {

            this.setSpeed(GameConfiguration.DEFAULT_SPEED);
        }

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
            acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (sneaking) {
            acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (jumping) {
            // handleJump();
            if (canJump) {
                maxFall = 0.5f;
                acceleration.y += 10.0f;
                canJump = false;
            }
        }

        if (breakingBlock) {
            if (canBreakBlock) {
                ChunkManager chunkManager = new ChunkManager();
                if (buildRay.getAimedChunk() != null && (buildRay.getAimedBlock() != Material.AIR.getId() || buildRay.getAimedBlock() != Material.WATER.getId())) {
                    chunkManager.removeBlock(buildRay.getAimedChunk(), buildRay.getBlockChunkPositionLocal(), Game.getInstance().getWorld());
                }
                canBreakBlock = false;
                breakBlockCooldown = (int) GameConfiguration.UPS / 3;
            }
        }

        if (breakBlockCooldown > 0) {
            breakBlockCooldown--;
            if (breakBlockCooldown == 0) {
                canBreakBlock = true;
            }
        }

        if (movingBackward || movingForward || movingLeft || movingRight) {
            hand.setAnimation(PlayerHandAnimation.MOVING);
        } else {
            hand.setAnimation(PlayerHandAnimation.IDLE);
        }

        velocity.add(acceleration.mul(speed));

        if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxSpeed);
            velocity.x = velocityNorm.x;
            velocity.z = velocityNorm.z;
        }

        if (new Vector3f(0, velocity.y, 0).length() > maxFall) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxFall);
            velocity.y = velocityNorm.y;
        }

        position.x += velocity.x;
        handleCollisions(world, new Vector3f(velocity.x, 0, 0));

        position.z += velocity.z;
        handleCollisions(world, new Vector3f(0, 0, velocity.z));

        position.y += velocity.y;
        handleCollisions(world, new Vector3f(0, velocity.y, 0));

        velocity.mul(0.95f);

        PlayerInputData inputData = new PlayerInputData(movingLeft, movingRight, movingForward, movingBackward, flying, sneaking, jumping, yaw, pitch, sprinting, placingBlock, breakingBlock);
        inputs.add(inputData);
    }

    public boolean isMoving() {
        return movingLeft || movingRight || movingForward || movingBackward || sneaking || flying || jumping;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getVelocity() {
        return velocity;
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


    public List<PlayerInputData> getInputs() {
        return inputs;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public Vector3f getGravity() {
        return gravity;
    }

    public Set<Coordinates> getReceivedChunks() {
        return receivedChunks;
    }

    public Vector3f getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Vector3f lastPosition) {
        this.lastPosition = lastPosition;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public PlayerHand getHand() {
        return hand;
    }

    public EntityUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(EntityUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean canJump() {
        return this.canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public float getMaxFallSpeed() {
        return maxFall;
    }

    public void setMaxFallSpeed(float maxFall) {
        this.maxFall = maxFall;
    }

    public Ray getAttackRay() {
        return attackRay;
    }

    public Ray getBuildRay() {
        return buildRay;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ArrayList<Vector3i> getAimedBlocks() {
        return aimedBlocks;
    }

    public void setAimedBlocks(ArrayList<Vector3i> aimedBlocks) {
        this.aimedBlocks = aimedBlocks;
    }
    
    public PlayerInventory getInventory() {
        return inventory;
    }

    public Hotbar getHotbar() {
        return hotbar;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }
}
