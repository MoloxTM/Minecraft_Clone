package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.player.Player;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PlayerMovePacket implements ClientPacket {

    private final Player player;
    private final ObjectMapper mapper;
    private final Logger logger;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingForward;
    private boolean movingBackward;

    public PlayerMovePacket(Player player) {
        this.player = player;
        this.mapper = new ObjectMapper();
        this.logger = LoggerUtility.getClientLogger(PlayerMovePacket.class, LogType.TXT);
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
    }

    @Override
    public void send() {
        if (!movingLeft && !movingRight && !movingBackward && !movingForward)
            return;

        MinecraftClient client = Game.getInstance().getClient();

        String message = this.toJSON();
        if (message == null) {
            logger.error("PlayerMovePacket: Impossible d'envoyer le packet, le JSON vaut null.");
            return;
        }
        try {
            String response = client.sendString(message);

            JsonNode positionNode = mapper.readTree(response);

            player.getPosition().x = positionNode.get("x").floatValue();
            player.getPosition().y = positionNode.get("y").floatValue();
            player.getPosition().z = positionNode.get("z").floatValue();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toJSON() {
        ObjectNode messageNode = mapper.createObjectNode();

        messageNode.put("playerName", player.getName());
        messageNode.put("uuid", player.getUuid());
        messageNode.put("clientVersion", "1.0.0");
        messageNode.put("type", "PLAYER_MOVE");
        messageNode.put("left", movingLeft);
        messageNode.put("right", movingRight);
        messageNode.put("forward", movingForward);
        messageNode.put("backward", movingBackward);
        messageNode.put("pitch", player.getPitch());
        messageNode.put("yaw", player.getYaw());

        try {
            return mapper.writeValueAsString(messageNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
    }
}
