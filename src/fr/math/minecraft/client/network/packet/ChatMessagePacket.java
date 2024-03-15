package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.player.Player;

public class ChatMessagePacket extends ClientPacket {

    private final Player sender;
    private final String message;

    public ChatMessagePacket(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();

        payload.put("type", "CHAT_MSG");
        payload.put("sender", sender.getUuid());
        payload.put("content", message);

        try {
            return mapper.writeValueAsString(payload);
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
