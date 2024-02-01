package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class SkinRequestPacket implements ClientPacket {

    private final String uuid;
    private final ObjectMapper mapper;
    private BufferedImage skin;
    private final static Logger logger = LoggerUtility.getClientLogger(SkinRequestPacket.class, LogType.TXT);

    public SkinRequestPacket(String uuid) {
        this.mapper = new ObjectMapper();
        this.uuid = uuid;
        this.skin = null;
    }

    @Override
    public void send() {
        MinecraftClient client = Game.getInstance().getClient();
        String message = this.toJSON();

        if (message == null) {
            return;
        }
        try {
            String base64Skin = client.sendString(message);
            if (base64Skin.contains("PLAYER_DOESNT_EXISTS") || base64Skin.contains("ERROR") || base64Skin.equalsIgnoreCase("TIMEOUT_REACHED")) {
                logger.error("Impossible d'envoyer le packet, le serveur a mis trop de temps à répondre ! (timeout)");
                return;
            }

            byte[] skinBytes = Base64.getDecoder().decode(base64Skin);
            skin = ImageIO.read(new ByteArrayInputStream(skinBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();

        node.put("type", "SKIN_REQUEST");
        node.put("uuid", uuid);

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public BufferedImage getSkin() {
        return skin;
    }

}
