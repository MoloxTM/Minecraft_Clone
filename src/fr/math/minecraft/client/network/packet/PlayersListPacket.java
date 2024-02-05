package fr.math.minecraft.client.network.packet;

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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class PlayersListPacket extends ClientPacket {

    private final ObjectMapper mapper;
    private final static Logger logger = LoggerUtility.getClientLogger(PlayersListPacket.class, LogType.TXT);;

    public PlayersListPacket() {
        this.mapper = new ObjectMapper();
    }

    private BufferedImage loadSkin(String base64Skin) {
        byte[] bytes = Base64.getDecoder().decode(base64Skin);
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toJSON() {
        ObjectNode packetNode = mapper.createObjectNode();
        packetNode.put("type", "PLAYERS_LIST_REQUEST");
        packetNode.put("uuid", Game.getInstance().getPlayer().getUuid());
        packetNode.put("clientVersion", "1.0.0");
        try {
            return mapper.writeValueAsString(packetNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public String getResponse() {
        return null;
    }

}
