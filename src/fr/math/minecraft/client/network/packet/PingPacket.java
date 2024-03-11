package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.player.Player;

public class PingPacket extends ClientPacket {

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        Player player = Game.getInstance().getPlayer();

        node.put("type", "PING_PACKET");
        node.put("uuid", player.getUuid());
        node.put("time", System.currentTimeMillis());

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
