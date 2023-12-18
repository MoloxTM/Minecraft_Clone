package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
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

            System.out.println(players);
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
