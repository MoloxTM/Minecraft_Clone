package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.MathUtils;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class StatePayload {

    private InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3f velocity;
    private List<BreakedBlock> breakedBlockData;
    private List<Vector3i> aimedPlacedBlockData;
    private List<Byte> materialAimedPlacedBlockData;
    private float yaw, pitch;

    private final static Logger logger = LoggerUtility.getClientLogger(StatePayload.class, LogType.TXT);


    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.breakedBlockData = new ArrayList<>();
        this.aimedPlacedBlockData = new ArrayList<>();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        this.breakedBlockData = new ArrayList<>();
        this.aimedPlacedBlockData = new ArrayList<>();
        this.materialAimedPlacedBlockData = new ArrayList<>();
        this.extractBrokenBlocks(stateData);
        //extractAimedPlacedBlockData(stateData);
        position.x = stateData.get("x").floatValue();
        position.y = stateData.get("y").floatValue();
        position.z = stateData.get("z").floatValue();
        velocity.x = stateData.get("vx").floatValue();
        velocity.y = stateData.get("vy").floatValue();
        velocity.z = stateData.get("vz").floatValue();
        yaw = stateData.get("yaw").floatValue();
        pitch = stateData.get("pitch").floatValue();
    }

    public void reconcileMovement(World world, Player player, Vector3f playerPosition, Vector3f playerVelocity) {
        Vector3f front = new Vector3f();
        //Vector3f position = new Vector3f(playerPosition);
        //Vector3f velocity = player.getVelocity();

        for (PlayerInputData inputData : payload.getInputData()) {
            float yaw = inputData.getYaw();
            float pitch = inputData.getPitch();

            this.yaw = yaw;
            this.pitch = pitch;

            Vector3f acceleration = new Vector3f(0,0,0);

            front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            front.y = (float) Math.sin(Math.toRadians(0.0f));
            front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

            front.normalize();
            Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

            player.getVelocity().add(player.getGravity());

            if (inputData.isMovingForward()) {
                acceleration.add(front);
            }

            if (inputData.isMovingBackward()) {
                acceleration.sub(front);
            }

            if (inputData.isMovingLeft()) {
                acceleration.sub(right);
            }

            if (inputData.isMovingRight()) {
                acceleration.add(right);
            }

            if (inputData.isFlying()) {
                acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isSneaking()) {
                acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isJumping()) {
                // this.handleJump();
                if (player.canJump()) {
                    player.setMaxFallSpeed(0.5f);
                    acceleration.y += 10.0f;
                    player.setCanJump(false);
                }
            }

            player.getVelocity().add(acceleration.mul(player.getSpeed()));

            if (new Vector3f(velocity.x, 0, velocity.z).length() > player.getMaxSpeed()) {
                Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
                velocityNorm.normalize().mul(player.getMaxSpeed());
                player.getVelocity().x = velocityNorm.x;
                player.getVelocity().z = velocityNorm.z;
            }

            if (new Vector3f(0, velocity.y, 0).length() > player.getMaxFallSpeed()) {
                Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
                velocityNorm.normalize().mul(player.getMaxFallSpeed());
                player.getVelocity().y = velocityNorm.y;
            }

            player.getPosition().x += player.getVelocity().x;
            player.handleCollisions(world, new Vector3f(player.getVelocity().x, 0, 0));

            player.getPosition().z += player.getVelocity().z;
            player.handleCollisions(world, new Vector3f(0, 0, player.getVelocity().z));

            player.getPosition().y += player.getVelocity().y;
            player.handleCollisions(world, new Vector3f(0, player.getVelocity().y, 0));

            player.getVelocity().mul(0.95f);
        }

        this.position = new Vector3f(player.getPosition());
    }

    public boolean verifyAimedPlacedBlocks(List<Vector3i> clientAimedPlacedBlockData) {
        if(clientAimedPlacedBlockData.isEmpty()) {
            return false;
        }
        for (int i = 0; i < aimedPlacedBlockData.size(); i++) {
            if(!clientAimedPlacedBlockData.get(i).equals(this.aimedPlacedBlockData.get(i))) {
                //logger.warn("Y'a problème avec :" + clientAimedPlacedBlockData.get(i));
                return true;
            }
        }

        return false;
    }

    public void verifyAimedBreakedBlocks(World world, List<BreakedBlock> clientBreakedBlocks) {
        for (BreakedBlock breakedBlock : clientBreakedBlocks) {
            if (!breakedBlockData.contains(breakedBlock)) {
                breakedBlock.restore(world);
                logger.info("[Reconciliation] Le block de " + Material.getMaterialById(breakedBlock.getBlock()) + " en " + breakedBlock.getPosition() + " a été restoré.");
            }
        }
        for (BreakedBlock breakedBlock : breakedBlockData) {
            Vector3i worldPosition = breakedBlock.getPosition();
            Chunk chunk = world.getChunkAt(worldPosition);

            int blockX = MathUtils.mod(worldPosition.x, Chunk.SIZE);
            int blockY = MathUtils.mod(worldPosition.y, Chunk.SIZE);
            int blockZ = MathUtils.mod(worldPosition.z, Chunk.SIZE);
            byte block = chunk.getBlock(blockX, blockY, blockZ);

            if (block != Material.AIR.getId()) {
                ChunkManager chunkManager = new ChunkManager();
                Vector3i localPosition = new Vector3i(blockX, blockY, blockZ);
                chunkManager.removeBlock(chunk, localPosition, world);
                logger.info("[Reconciliation] Le serveur a cassé le block de " + Material.getMaterialById(block) + " en " + breakedBlock.getPosition());
            }
        }
    }

    public void send(Player player) {
        PlayerMovePacket packet = new PlayerMovePacket(player, this, payload);
        FixedPacketSender.getInstance().enqueue(packet);
    }

    public void extractBrokenBlocks(JsonNode data) {
        ArrayNode positionNode = (ArrayNode) data.get("aimedBreakedBlocks");
        for (int i = 0; i < positionNode.size(); i++) {
            JsonNode node = positionNode.get(i);
            Vector3i position = new Vector3i(node.get("x").asInt(), node.get("y").asInt(), node.get("z").asInt());
            byte block = (byte) node.get("block").asInt();
            BreakedBlock breakedBlock = new BreakedBlock(position, block);
            this.breakedBlockData.add(breakedBlock);
        }

    }

    public void extractAimedPlacedBlockData(JsonNode data) {
        ArrayNode positionNode = (ArrayNode) data.get("aimedPlacedBlocks");
        for (int i = 0; i < positionNode.size(); i++) {
            JsonNode node = positionNode.get(i);
            Vector3i position = new Vector3i(node.get("x").asInt(), node.get("y").asInt(), node.get("z").asInt());
            this.aimedPlacedBlockData.add(position);
            this.materialAimedPlacedBlockData.add(((byte)node.get("block").asInt()));
        }

    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public Vector3f getPosition() {
        return position;
    }

    public InputPayload getInputPayload() {
        return payload;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setBreakedBlockData(List<BreakedBlock> breakedBlockData) {
        this.breakedBlockData = breakedBlockData;
    }

    public List<BreakedBlock> getBreakedBlockData() {
        return breakedBlockData;
    }

    public void setAimedPlacedBlockData(List<Vector3i> aimedPlacedBlockData) {
        this.aimedPlacedBlockData = aimedPlacedBlockData;
    }

    public List<Vector3i> getAimedPlacedBlockData() {
        return aimedPlacedBlockData;
    }
}
