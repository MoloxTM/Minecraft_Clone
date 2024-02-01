package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.handler.PlayerMovementHandler;
import fr.math.minecraft.client.network.payload.InputPayload;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PlayerMovePacket implements ClientPacket {

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

    public PlayerMovePacket(StatePayload statePayload, InputPayload inputPayload) {
        this.statePayload = statePayload;
        this.tick = inputPayload.getTick();
        this.mapper = new ObjectMapper();
        this.movingLeft = inputPayload.isMovingLeft();
        this.movingRight = inputPayload.isMovingRight();
        this.movingForward = inputPayload.isMovingForward();
        this.movingBackward = inputPayload.isMovingBackward();
        this.flying = inputPayload.isFlying();
        this.sneaking = inputPayload.isSneaking();
        this.movingHead = false;
    }

    @Override
    public void send() {

        Game game = Game.getInstance();
        MinecraftClient client = game.getClient();
        PlayerMovementHandler handler = game.getPlayerMovementHandler();

        String message = this.toJSON();
        if (message == null) {
            logger.error("PlayerMovePacket: Impossible d'envoyer le packet, le JSON vaut null.");
            return;
        }
        try {
            String response = client.sendString(message);

            if (response.equalsIgnoreCase("TIMEOUT_REACHED")) {
                logger.error("Impossible d'envoyer le packet, le serveur a mis trop de temps à répondre ! (timeout)");
                return;
            }

            JsonNode positionData = mapper.readTree(response);
            statePayload.setData(positionData);

            handler.setLastServerState(statePayload);

            /*
            player.getPosition().x = positionNode.get("x").floatValue();
            player.getPosition().y = positionNode.get("y").floatValue();
            player.getPosition().z = positionNode.get("z").floatValue();
             */

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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

    public StatePayload getStatePayload() {
        return statePayload;
    }
}
