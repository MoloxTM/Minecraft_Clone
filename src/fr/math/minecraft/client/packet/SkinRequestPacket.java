package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class SkinRequestPacket implements ClientPacket {

    private final String uuid;
    private final ObjectMapper mapper;
    private BufferedImage skin;

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
            if (base64Skin.contains("PLAYER_DOESNT_EXISTS") || base64Skin.contains("ERROR")) {
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
