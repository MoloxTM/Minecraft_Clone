package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.shared.network.PlayerInputData;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class StatePayload {

    private InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3f velocity;
    private Vector3i inputVector;
    private float yaw, pitch;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.inputVector = new Vector3i();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        position.x = stateData.get("x").floatValue();
        position.y = stateData.get("y").floatValue();
        position.z = stateData.get("z").floatValue();
        velocity.x = stateData.get("vx").floatValue();
        velocity.y = stateData.get("vy").floatValue();
        velocity.z = stateData.get("vz").floatValue();
        yaw = stateData.get("yaw").floatValue();
        pitch = stateData.get("pitch").floatValue();
    }

    public void predictMovement(Player player, Vector3f playerPosition, Vector3f playerVelocity) {
        Vector3f front = new Vector3f();
        Vector3f position = new Vector3f(playerPosition);
        Vector3f velocity = player.getVelocity();

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

            //velocity.add(player.getGravity());

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
                acceleration.sub(new Vector3f(0.0f, .1f, 0.0f));
            }

            velocity.add(acceleration.mul(player.getSpeed()));

            if (velocity.length() > player.getMaxSpeed()) {
                velocity.normalize().mul(player.getMaxSpeed());
            }


            player.getPosition().x += velocity.x;
            player.handleCollisions(new Vector3f(velocity.x, 0, 0));

            player.getPosition().z += velocity.z;
            player.handleCollisions(new Vector3f(0, 0, velocity.z));

            player.getPosition().y += velocity.y;
            player.handleCollisions(new Vector3f(0, velocity.y, 0));

            velocity.mul(0.95f);
        }

        this.position = new Vector3f(position);
    }

    public void send(Player player) {
        PlayerMovePacket packet = new PlayerMovePacket(player, this, payload);
        FixedPacketSender.getInstance().enqueue(packet);
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
}
