package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.entity.Player;

public class InputPayload {

    private final int tick;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean sneaking, flying;
    private final String clientUuid;

    public InputPayload(JsonNode payloadData) {
        this.tick = payloadData.get("tick").asInt();
        this.clientUuid = payloadData.get("uuid").asText();
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
}
