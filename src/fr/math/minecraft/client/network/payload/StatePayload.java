package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class StatePayload {

    private InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3i inputVector;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.inputVector = payload.getInputVector();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.inputVector = new Vector3i();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        position.x = stateData.get("x").floatValue();
        position.y = stateData.get("y").floatValue();
        position.z = stateData.get("z").floatValue();
        inputVector.x = stateData.get("inputX").asInt();
        inputVector.y = stateData.get("inputY").asInt();
        inputVector.z = stateData.get("inputZ").asInt();
    }

    public void predictMovement(Player player) {

        Vector3f front = new Vector3f();
        float yaw = payload.getYaw();
        float pitch = payload.getPitch();
        float speed = player.getSpeed();

        Vector3i inputVector = new Vector3i(this.inputVector);

        front.x = (float) (Math.cos(Math.toRadians(yaw) * Math.cos(Math.toRadians(pitch))));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw) * Math.cos(Math.toRadians(pitch))));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f position = new Vector3f(player.getPosition());

        while (inputVector.z < 0) {
            position.add(new Vector3f(front).mul(speed));
            inputVector.z++;
        }

        while (inputVector.z > 0) {
            position.sub(new Vector3f(front).mul(speed));
            inputVector.z--;
        }

        while (inputVector.x < 0) {
            position.sub(new Vector3f(right).mul(speed));
            inputVector.x++;
        }

        while (inputVector.x > 0) {
            position.add(new Vector3f(right).mul(speed));
            inputVector.x--;
        }

        while (inputVector.y > 0) {
            position.add(new Vector3f(0.0f, .5f, 0.0f));
            inputVector.y--;
        }

        while (inputVector.y < 0) {
            position.sub(new Vector3f(0.0f, .5f, 0.0f));
            inputVector.y++;
        }

        this.position = new Vector3f(position);
    }

    public void send() {
        PlayerMovePacket packet = new PlayerMovePacket(this, payload, inputVector);
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
}
