package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.entity.Player;
import org.joml.Vector3i;

public class InputPayload {

    private final int tick;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean sneaking, flying;
    private final String clientUuid;
    private final Vector3i inputVector;
    private final float yaw;
    private final float pitch;

    public InputPayload(JsonNode payloadData) {
        this.tick = payloadData.get("tick").asInt();
        this.clientUuid = payloadData.get("uuid").asText();
        this.inputVector = new Vector3i();
        inputVector.x = payloadData.get("inputX").asInt();
        inputVector.y = payloadData.get("inputY").asInt();
        inputVector.z = payloadData.get("inputZ").asInt();
        this.yaw = payloadData.get("yaw").floatValue();
        this.pitch = payloadData.get("pitch").floatValue();
        this.movingLeft = payloadData.get("left").asBoolean();
        this.movingRight = payloadData.get("right").asBoolean();
        this.movingForward = payloadData.get("forward").asBoolean();
        this.movingBackward = payloadData.get("backward").asBoolean();
        this.flying = payloadData.get("flying").asBoolean();
        this.sneaking = payloadData.get("sneaking").asBoolean();
    }

    public int getTick() {
        return tick;
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

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isFlying() {
        return flying;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public Vector3i getInputVector() {
        return inputVector;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
