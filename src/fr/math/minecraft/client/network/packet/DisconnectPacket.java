package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;

public class DisconnectPacket extends ClientPacket {

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("type", "PLAYER_DISCONNECT");
        node.put("uuid", Game.getInstance().getPlayer().getUuid());

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getResponse() {
        return null;
    }

}
