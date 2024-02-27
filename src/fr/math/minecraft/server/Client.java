package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
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
    private boolean canBreakBlock;
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
    private final Ray buildRay, attackRay;
    private List<Vector3i> aimedBlocks;
    private List<Byte> aimedBlocksIDs;
    private int breakBlockCooldown;
    private final static Logger logger = LoggerUtility.getServerLogger(Client.class, LogType.TXT);

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
        this.skin = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingBackward = false;
        this.movingForward = false;
        this.flying = false;
        this.sneaking = false;
        this.canJump = true;
        this.canBreakBlock = true;
        this.active = false;
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.attackRay = new Ray(GameConfiguration.ATTACK_REACH);
        this.aimedBlocks = new ArrayList<>();
        this.aimedBlocksIDs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void handleInputs(JsonNode packetData) {
        boolean movingLeft = packetData.get("left").asBoolean();
        boolean movingRight = packetData.get("right").asBoolean();
        boolean movingForward = packetData.get("forward").asBoolean();
        boolean movingBackward = packetData.get("backward").asBoolean();
        boolean flying = packetData.get("flying").asBoolean();
        boolean sneaking = packetData.get("sneaking").asBoolean();

        float yaw = packetData.get("yaw").floatValue();
        float bodyYaw = packetData.get("bodyYaw").floatValue();
        float pitch = packetData.get("pitch").floatValue();

        int inputX = packetData.get("inputX").intValue();
        int inputY = packetData.get("inputY").intValue();
        int inputZ = packetData.get("inputZ").intValue();

        this.yaw = yaw;
        this.bodyYaw = bodyYaw;
        this.pitch = pitch;

        this.movingLeft = movingLeft;
        this.movingRight = movingRight;
        this.movingForward = movingForward;
        this.movingBackward = movingBackward;

        this.inputVector.x = inputX;
        this.inputVector.y = inputY;
        this.inputVector.z = inputZ;

        this.flying = flying;
        this.sneaking = sneaking;
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

            if (!inputData.isBreakingBlock()) {
                canBreakBlock = true;
            }

            if (inputData.isBreakingBlock()) {

                byte block = buildRay.getAimedBlock();

                if (this.canBreakBlock) {
                    if (buildRay.getAimedChunk() != null && block != Material.AIR.getId() && block != Material.WATER.getId()) {

                        Vector3i rayPosition = buildRay.getBlockWorldPosition();
                        Vector3i blockPositionLocal = Utils.worldToLocal(rayPosition);

                        blocksPosition.add(rayPosition);
                        blocksIDs.add(block);

                        logger.info(name + " (" + uuid + ") a cassé un block de " + Material.getMaterialById(block) + " en " + buildRay.getBlockWorldPosition());

                        buildRay.getAimedChunk().setBlock(blockPositionLocal.x, blockPositionLocal.y, blockPositionLocal.z, Material.AIR.getId());
                        buildRay.reset();
                        this.canBreakBlock = false;
                    }
                }
            }

            velocity.mul(0.95f);
        }

        this.aimedBlocks = blocksPosition;
        this.aimedBlocksIDs = blocksIDs;
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
        node.put("movingLeft", movingLeft);
        node.put("movingRight", movingRight);
        node.put("movingForward", movingForward);
        node.put("movingBackward", movingBackward);
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

    public List<Byte> getAimedBlocksIDs() {
        return aimedBlocksIDs;
    }

    public List<Vector3i> getAimedBlocks() {
        return aimedBlocks;
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

}
