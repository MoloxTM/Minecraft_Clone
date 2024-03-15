package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.inventory.DroppedItem;
import fr.math.minecraft.shared.inventory.PlayerInventory;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class Client {

    private final String name;
    private final String uuid;
    private final InetAddress address;
    private final int port;
    private Vector3f position;
    private Vector3f front;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private BufferedImage skin;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking;
    private boolean canBreakBlock, canPlaceBlock;
    private boolean active;
    private final Vector3i inputVector;
    private final Vector3f velocity;
    private final Vector3f gravity;
    private final Vector3f acceleration;
    private float maxSpeed;
    private float maxFallSpeed;
    private final Hitbox hitbox;
    private Vector3f lastChunkPosition;
    private final Queue<InputPayload> inputQueue;
    private final StatePayload[] stateBuffer;
    private boolean canJump;
    private final Ray buildRay, attackRay, breakRay;
    private List<Vector3i> aimedPLacedBlocks;
    private List<BreakedBlock> breakedBlocks;
    private List<Byte> aimedPLacedBlocksIDs, aimedBreakedBlocksIDs;;
    private int breakBlockCooldown, placeBlockCoolDown;
    private List<Vector3i> aimedBlocks;
    private List<Byte> aimedBlocksIDs;
    private Sprite sprite;
    private PlayerAction action;
    private final static Logger logger = LoggerUtility.getServerLogger(Client.class, LogType.TXT);
    private final PlayerInventory inventory;

    public Client(String uuid, String name, InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.uuid = uuid;
        this.name = name;
        this.velocity = new Vector3f();
        this.inputQueue = new LinkedList<>();
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.acceleration = new Vector3f();
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.position = new Vector3f(0.0f, 300.0f, 0.0f);
        this.lastChunkPosition = new Vector3f(0, 0, 0);
        this.inputVector = new Vector3i(0, 0, 0);
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 1.0f, 0.25f));
        this.stateBuffer = new StatePayload[GameConfiguration.BUFFER_SIZE];
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = GameConfiguration.DEFAULT_SPEED;
        this.maxSpeed = 0.03f;
        this.maxFallSpeed = 0.03f;
        this.breakBlockCooldown = GameConfiguration.BLOCK_BREAK_COOLDOWN;
        this.placeBlockCoolDown= GameConfiguration.BLOCK_BREAK_COOLDOWN;
        this.skin = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingBackward = false;
        this.movingForward = false;
        this.flying = false;
        this.sneaking = false;
        this.canJump = true;
        this.canBreakBlock = true;
        this.canPlaceBlock = true;
        this.active = false;
        this.sprite = new Sprite();
        this.action = PlayerAction.MINING;
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.breakRay = new Ray(GameConfiguration.BREAKING_REACH);
        this.attackRay = new Ray(GameConfiguration.ATTACK_REACH);
        this.aimedPLacedBlocks = new ArrayList<>();
        this.aimedPLacedBlocksIDs = new ArrayList<>();
        this.breakedBlocks = new ArrayList<>();
        this.aimedBreakedBlocksIDs = new ArrayList<>();
        this.aimedBlocks = new ArrayList<>();
        this.aimedBlocksIDs = new ArrayList<>();
        this.inventory = new PlayerInventory();
    }

    public String getName() {
        return name;
    }

    public void handleCollisions(Vector3f velocity) {
        MinecraftServer server = MinecraftServer.getInstance();
        World world = server.getWorld();

        int minX = (int) Math.floor(position.x - hitbox.getWidth());
        int maxX = (int) Math.ceil(position.x + hitbox.getWidth());
        int minY = (int) Math.floor(position.y - hitbox.getHeight());
        int maxY = (int) Math.ceil(position.y + hitbox.getHeight());
        int minZ = (int) Math.floor(position.z - hitbox.getDepth());
        int maxZ = (int) Math.ceil(position.z + hitbox.getDepth());

        for (int worldX = minX; worldX < maxX; worldX++) {
            for (int worldY = minY; worldY < maxY; worldY++) {
                for (int worldZ = minZ; worldZ < maxZ; worldZ++) {

                    byte block = world.getServerBlockAt(worldX, worldY, worldZ);
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
                        maxFallSpeed = 0.03f;
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

    public void update(World world, InputPayload payload) {

        breakedBlocks = new ArrayList<>();
        List<Vector3i> blocksPosition = new ArrayList<>();
        List<Byte> blocksIDs = new ArrayList<>();

        for (PlayerInputData inputData : payload.getInputsData()) {
            float yaw = inputData.getYaw();
            float pitch = inputData.getPitch();

            this.yaw = yaw;
            this.pitch = pitch;

            Vector3f acceleration = new Vector3f(0, 0, 0);

            front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            front.y = (float) Math.sin(Math.toRadians(0.0f));
            front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

            Vector3f cameraFront = new Vector3f(front);
            cameraFront.y = Math.sin(Math.toRadians(pitch));

            front.normalize();
            Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

            this.resetMoving();

            velocity.add(gravity);

            if (inputData.isMovingForward()) {
                acceleration.add(front);
                movingForward = true;
            }

            if (inputData.isMovingBackward()) {
                acceleration.sub(front);
                movingBackward = true;
            }

            if (inputData.isMovingLeft()) {
                acceleration.sub(right);
                movingLeft = true;
            }

            if (inputData.isMovingRight()) {
                acceleration.add(right);
                movingRight = true;
            }

            if (inputData.isFlying()) {
                acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isSneaking()) {
                acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isJumping()) {
                // this.handleJump();
                if (canJump) {
                    maxFallSpeed = 0.5f;
                    acceleration.y += 10.0f;
                    canJump = false;
                }
            }

            velocity.add(acceleration.mul(speed));

            if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
                Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
                velocityNorm.normalize().mul(maxSpeed);
                velocity.x = velocityNorm.x;
                velocity.z = velocityNorm.z;
            }

            if (new Vector3f(0, velocity.y, 0).length() > maxFallSpeed) {
                Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
                velocityNorm.normalize().mul(maxFallSpeed);
                velocity.y = velocityNorm.y;
            }

            position.x += velocity.x;
            handleCollisions(new Vector3f(velocity.x, 0, 0));

            position.z += velocity.z;
            handleCollisions(new Vector3f(0, 0, velocity.z));

            position.y += velocity.y;
            handleCollisions(new Vector3f(0, velocity.y, 0));

            buildRay.update(position, cameraFront, world, true);
            breakRay.update(position, cameraFront, world, true);

            if (!breakRay.isAimingBlock()) {
                sprite.reset();
                breakRay.reset();
                action = null;
            }

            if (!inputData.isBreakingBlock()) {
                canBreakBlock = true;
            }

            if (inputData.isBreakingBlock()) {

                byte block = breakRay.getAimedBlock();
                sprite.update(PlayerAction.MINING);

                if (action == PlayerAction.MINING && breakRay.isAimingBlock() && sprite.getIndex() == action.getLength() - 1) {
                    Vector3i rayPosition = breakRay.getBlockWorldPosition();
                    Vector3i blockPositionLocal = Utils.worldToLocal(rayPosition);
                    Material material = Material.getMaterialById(block);

                    BreakedBlock breakedBlock = new BreakedBlock(rayPosition, block);
                    breakedBlocks.add(breakedBlock);

                    logger.info(name + " (" + uuid + ") a cassé un block de " + material + " en " + breakRay.getBlockWorldPosition());

                    breakRay.getAimedChunk().setBlock(blockPositionLocal.x, blockPositionLocal.y, blockPositionLocal.z, Material.AIR.getId());
                    breakRay.reset();
                    sprite.reset();

                    Random random = new Random();
                    DroppedItem droppedItem = new DroppedItem(new Vector3f(rayPosition), material);
                    droppedItem.getVelocity().y = 0.8f;
                    droppedItem.getVelocity().x = random.nextFloat(0.35f, 0.75f);
                    droppedItem.getVelocity().z = random.nextFloat(0.3f, 0.85f);

                    world.getDroppedItems().put(droppedItem.getUuid(), droppedItem);
                }

                if (this.canBreakBlock) {
                    if (breakRay.isAimingBlock()) {
                        action = PlayerAction.MINING;
                        sprite.reset();
                    }
                    this.canBreakBlock = false;
                }
            } else {
                sprite.reset();
            }


            if (!inputData.isPlacingBlock()) {
                canPlaceBlock = true;
            }

            if (inputData.isPlacingBlock()) {

                byte block = buildRay.getAimedBlock();

                if (this.canPlaceBlock) {
                    if (buildRay.isAimingBlock()) {

                        Vector3i rayPosition = buildRay.getBlockWorldPosition();
                        Vector3i placedBlock = buildRay.getBlockPlacedPosition(rayPosition);

                        Vector3i blockPositionLocal = Utils.worldToLocal(placedBlock);

                        blocksPosition.add(placedBlock);

                        /*On détermine le chunk où le */
                        Chunk aimedChunk = world.getChunkAt(placedBlock);
                        System.out.println("Blocks IDS:" + blocksIDs);
                        blocksIDs.add(aimedChunk.getBlock(blockPositionLocal));

                        aimedChunk.setBlock(blockPositionLocal.x, blockPositionLocal.y, blockPositionLocal.z, Material.STONE.getId());

                        logger.info(name + " (" + uuid + ") a placé un block de " + Material.getMaterialById(block) + " en " + buildRay.getBlockWorldPosition());

                        buildRay.reset();
                        this.canPlaceBlock = false;
                    }
                }
                this.aimedPLacedBlocks = blocksPosition;
                this.aimedPLacedBlocksIDs = blocksIDs;
            }

            velocity.mul(0.95f);
        }



        // System.out.println("Tick " + payload.getTick() + " InputVector: " + payload.getInputVector() + " Calculated position : " + position);
    }

    private void handleJump() {
        if (canJump) {
            velocity.y += gravity.y * (2.5f - 1) * 1.0f / GameConfiguration.UPS;
            canJump = false;
            maxFallSpeed = 1f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public ObjectNode toJSONWithSkin() {
        ObjectNode node = this.toJSON();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skin, "png", baos);
            node.put("skin", Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return node;
    }

    public void resetMoving() {
        this.movingRight = false;
        this.movingLeft = false;
        this.movingBackward = false;
        this.movingForward = false;
    }

    public ObjectNode toJSON() {
        ObjectNode node = new ObjectMapper().createObjectNode();

        node.put("name", this.name);
        node.put("uuid", this.uuid);
        node.put("x", this.position.x);
        node.put("y", this.position.y);
        node.put("z", this.position.z);
        node.put("vx", this.velocity.x);
        node.put("vy", this.velocity.y);
        node.put("vz", this.velocity.z);
        node.put("rx", this.buildRay.getBlockWorldPosition().x);
        node.put("ry", this.buildRay.getBlockWorldPosition().y);
        node.put("rz", this.buildRay.getBlockWorldPosition().z);
        node.put("movingLeft", movingLeft);
        node.put("movingRight", movingRight);
        node.put("movingForward", movingForward);
        node.put("movingBackward", movingBackward);
        node.put("action", action == null ? "NONE" : action.toString());
        node.put("spriteIndex", sprite.getIndex());
        node.put("yaw", this.yaw);
        node.put("pitch", this.pitch);
        node.put("bodyYaw", this.bodyYaw);

        return node;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public String getUuid() {
        return uuid;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Queue<InputPayload> getInputQueue() {
        return inputQueue;
    }

    public StatePayload[] getStateBuffer() {
        return stateBuffer;
    }

    public List<Byte> getAimedPLacedBlocksIDs() {
        return aimedPLacedBlocksIDs;
    }

    public List<Vector3i> getAimedPLacedBlocks() {
        return aimedPLacedBlocks;
    }

    public List<Byte> getAimedBreakedBlocksIDs() {
        return aimedBreakedBlocksIDs;
    }

    public List<BreakedBlock> getBreakedBlocks() {
        return breakedBlocks;
    }

    public Vector3f getFront() {
        return front;
    }

    public Vector3f getGravity() {
        return gravity;
    }

    public Ray getAttackRay() {
        return attackRay;
    }

    public Ray getBuildRay() {
        return buildRay;
    }

    public Ray getBreakRay() {
        return breakRay;
    }
    
    public PlayerInventory getInventory() {
        return inventory;
    }
}
