package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.payload.InputPayload;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;

public class PlayerMovePacket extends ClientPacket {

    private final ObjectMapper mapper;
    private final static Logger logger = LoggerUtility.getClientLogger(PlayerMovePacket.class, LogType.TXT);;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingForward;
    private boolean movingBackward;
    private boolean flying;
    private boolean sneaking;
    private boolean movingHead;
    private final StatePayload statePayload;
    private final int tick;
    private String response;
    private final Vector3i inputVector;

    public PlayerMovePacket(StatePayload statePayload, InputPayload inputPayload, Vector3i inputVector) {
        this.statePayload = statePayload;
        this.tick = inputPayload.getTick();
        this.mapper = new ObjectMapper();
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.flying = inputPayload.isFlying();
        this.sneaking = inputPayload.isSneaking();
        this.inputVector = inputVector;
        this.movingHead = false;
        this.response = "";
    }

    @Override
    public String toJSON() {
        ObjectNode messageNode = mapper.createObjectNode();
        Player player = Game.getInstance().getPlayer();

        messageNode.put("tick", tick);
        messageNode.put("playerName", player.getName());
        messageNode.put("uuid", player.getUuid());
        messageNode.put("clientVersion", "1.0.0");
        messageNode.put("type", "PLAYER_MOVE");
        messageNode.put("left", movingLeft);
        messageNode.put("right", movingRight);
        messageNode.put("forward", movingForward);
        messageNode.put("backward", movingBackward);
        messageNode.put("inputX", inputVector.x);
        messageNode.put("inputY", inputVector.y);
        messageNode.put("inputZ", inputVector.z);
        messageNode.put("flying", flying);
        messageNode.put("sneaking", sneaking);
        messageNode.put("pitch", player.getPitch());
        messageNode.put("yaw", player.getYaw());
        messageNode.put("bodyYaw", player.getBodyYaw());

        try {
            return mapper.writeValueAsString(messageNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getResponse() {
        return response;
    }

    public StatePayload getStatePayload() {
        return statePayload;
    }
}
