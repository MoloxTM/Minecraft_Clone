package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.player.Player;
import fr.math.minecraft.client.player.PlayerDirection;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PlayerMovePacket implements ClientPacket {

    private final PlayerDirection direction;
    private final Player player;
    private final ObjectMapper mapper;
    private final Logger logger;

    public PlayerMovePacket(Player player, PlayerDirection direction) {
        this.player = player;
        this.direction = direction;
        this.mapper = new ObjectMapper();
        this.logger = LoggerUtility.getClientLogger(PlayerMovePacket.class, LogType.TXT);
    }

    @Override
    public void send() {
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
        messageNode.put("direction", direction.toString());
        messageNode.put("pitch", player.getPitch());
        messageNode.put("yaw", player.getYaw());
        try {
            return mapper.writeValueAsString(messageNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
