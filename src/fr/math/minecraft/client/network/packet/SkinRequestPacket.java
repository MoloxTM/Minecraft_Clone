package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;

public class SkinRequestPacket extends ClientPacket {

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

    @Override
    public String getResponse() {
        return null;
    }

    public BufferedImage getSkin() {
        return skin;
    }

}
