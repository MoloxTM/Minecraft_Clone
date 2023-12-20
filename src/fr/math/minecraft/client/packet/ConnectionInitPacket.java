package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.player.Player;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ConnectionInitPacket implements ClientPacket {

    private final ObjectMapper mapper;
    private final Player player;
    private final Logger logger;
    private String giftedId;

    public ConnectionInitPacket(Player player) {
        this.mapper = new ObjectMapper();
        this.player = player;
        this.logger = LoggerUtility.getClientLogger(ConnectionInitPacket.class, LogType.TXT);
    }
    @Override
    public void send() {
        MinecraftClient client = Game.getInstance().getClient();
        String message = this.toJSON();

        if (message == null)
            return;

        try {
            String id = client.sendString(message);

            if (id.contains("USERNAME_NOT_AVAILABLE")) {
                throw new RuntimeException("Le joueur " + player.getName() + " est déjà connecté !");
            }

            player.setUuid(id.substring(0, 36));
            logger.info("Connection initié, ID offert : " + player.getUuid());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CONNECTION_INIT");
        node.put("playerName", player.getName());
        node.put("clientVersion", "1.0.0");

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getGiftedId() {
        return giftedId;
    }
}
