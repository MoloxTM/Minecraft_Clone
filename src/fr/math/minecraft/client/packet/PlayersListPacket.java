package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PlayersListPacket implements ClientPacket {

    private final ObjectMapper mapper;
    private final Logger logger;

    public PlayersListPacket() {
        this.mapper = new ObjectMapper();
        this.logger = LoggerUtility.getClientLogger(PlayersListPacket.class, LogType.TXT);
    }
    @Override
    public void send() {
        Game game = Game.getInstance();
        MinecraftClient client = game.getClient();
        String message = this.toJSON();
        try {
            String players = client.sendString(message);

            ArrayNode playersNode = (ArrayNode) mapper.readTree(players);
            for (int i = 0; i < playersNode.size(); i++) {
                JsonNode playerNode = playersNode.get(i);

                String uuid = playerNode.get("uuid").asText();
                Player player;

                if (uuid.equalsIgnoreCase(game.getPlayer().getUuid())) continue;

                if (game.getPlayers().containsKey(uuid)) {
                    player = game.getPlayers().get(uuid);
                } else {
                    player = new Player(playerNode.get("name").asText());
                    player.setUuid(uuid);
                    game.getPlayers().put(uuid, player);
                }

                float playerX = playerNode.get("x").floatValue();
                float playerY = playerNode.get("y").floatValue();
                float playerZ = playerNode.get("z").floatValue();
                float pitch = playerNode.get("pitch").floatValue();
                float yaw = playerNode.get("yaw").floatValue();

                player.getPosition().x = playerX;
                player.getPosition().y = playerY;
                player.getPosition().z = playerZ;

                player.setYaw(yaw);
                player.setPitch(pitch);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toJSON() {
        ObjectNode packetNode = mapper.createObjectNode();
        packetNode.put("type", "PLAYERS_LIST_REQUEST");
        packetNode.put("clientVersion", "1.0.0");
        try {
            return mapper.writeValueAsString(packetNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
