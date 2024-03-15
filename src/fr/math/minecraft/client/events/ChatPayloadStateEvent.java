package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.server.payload.ChatStatePayload;

public class ChatPayloadStateEvent {

    private final ArrayNode chatData;

    public ChatPayloadStateEvent(ArrayNode chatData) {
        this.chatData = chatData;
    }

    public ArrayNode getChatData() {
        return chatData;
    }
}
