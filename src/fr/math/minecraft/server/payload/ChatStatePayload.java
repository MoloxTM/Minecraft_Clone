package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.shared.ChatMessage;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.List;

public class ChatStatePayload {

    private final List<ChatMessage> chatMessages;

    public ChatStatePayload(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void send(Client client) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();
        ArrayNode chatArray = mapper.createArrayNode();
        MinecraftServer server = MinecraftServer.getInstance();

        for (int i = chatMessages.size() - 1; i >= 0 && i >= chatMessages.size() - 1 - 20; i--) {
            ChatMessage chatMessage = chatMessages.get(i);
            ObjectNode messageNode = chatMessage.toJSONObject();

            chatArray.add(messageNode);
        }

        payload.put("type", "CHAT_PAYLOAD");
        payload.set("chat", chatArray);

        try {
            byte[] buffer = mapper.writeValueAsBytes(payload);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
            server.sendPacket(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
