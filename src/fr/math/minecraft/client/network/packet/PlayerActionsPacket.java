package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.client.network.payload.InputPayload;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

public class PlayerActionsPacket extends ClientPacket {

    private final ObjectMapper mapper;
    private final static Logger logger = LoggerUtility.getClientLogger(PlayerActionsPacket.class, LogType.TXT);;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingForward;
    private boolean movingBackward;
    private boolean jumping;
    private boolean flying;
    private boolean sneaking;
    private boolean movingHead;
    private final StatePayload statePayload;
    private final int tick;
    private String response;
    private final Player player;

    public PlayerActionsPacket(Player player, StatePayload statePayload, InputPayload inputPayload) {
        this.player = player;
        this.statePayload = statePayload;
        this.tick = inputPayload.getTick();
        this.mapper = new ObjectMapper();
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.flying = false;
        this.sneaking = false;
        this.movingHead = false;
        this.response = "";
    }

    @Override
    public String toJSON() {
        ObjectNode messageNode = mapper.createObjectNode();
        ArrayNode inputsNode = mapper.createArrayNode();

        for (PlayerInputData inputData : statePayload.getInputPayload().getInputData()) {
            inputsNode.add(inputData.toJSON());
        }

        messageNode.put("tick", tick);
        messageNode.put("playerName", player.getName());
        messageNode.put("uuid", player.getUuid());
        messageNode.put("clientVersion", "1.0.0");
        messageNode.put("type", "PLAYER_ACTIONS");
        messageNode.set("inputs", inputsNode);
        messageNode.put("bodyYaw", player.getBodyYaw());

        try {
            return mapper.writeValueAsString(messageNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
