package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.World;
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
    private Vector3i inputVector;
    private List<Vector3i> aimedBlockData;
    private List<Byte> materialAimedBlockData;
    private float yaw, pitch;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.aimedBlockData = new ArrayList<>();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.inputVector = new Vector3i();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        this.aimedBlockData = new ArrayList<>();
        this.materialAimedBlockData = new ArrayList<>();
        extractAimedBlockData(stateData);
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

    public void verifyAimedBlocks(List<Vector3i> clientAimedBlockData) {
        for (int i = 0; i < clientAimedBlockData.size(); i++) {
            if(!clientAimedBlockData.get(i).equals(this.aimedBlockData.get(i))) {
                System.out.println("Y'a problème avec :" + clientAimedBlockData.get(i));
            }
        }

    }

    public void send(Player player) {
        PlayerMovePacket packet = new PlayerMovePacket(player, this, payload);
        FixedPacketSender.getInstance().enqueue(packet);
    }

    public void extractAimedBlockData(JsonNode data) {
        ArrayNode positionNode = (ArrayNode) data.get("aimedBlocks");
        for (int i = 0; i < positionNode.size(); i++) {
            JsonNode node = positionNode.get(i);
            Vector3i position = new Vector3i(node.get("x").asInt(), node.get("y").asInt(), node.get("z").asInt());
            this.aimedBlockData.add(position);
            this.materialAimedBlockData.add(((byte)node.get("block").asInt()));
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

    public void setAimedBlockData(List<Vector3i> aimedBlockData) {
        this.aimedBlockData = aimedBlockData;
    }

    public List<Vector3i> getAimedBlockData() {
        return aimedBlockData;
    }
}
