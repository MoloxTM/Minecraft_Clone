package fr.math.minecraft.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

public class ChatMessage {

    private final String id;
    private final String senderUuid;
    private final String senderName;
    private final String message;
    private final long timestamp;

    public ChatMessage(String senderUuid, String senderName, String message) {
        this(UUID.randomUUID().toString(), System.currentTimeMillis(), senderUuid, senderName, message);
    }

    public ChatMessage(String id, long timestamp, String senderUuid, String senderName, String message) {
        this.timestamp = timestamp;
        this.id = id;
        this.senderUuid = senderUuid;
        this.senderName = senderName;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("timestamp", timestamp);
        node.put("id", id);
        node.put("uuid", senderUuid);
        node.put("name", senderName);
        node.put("message", message);

        return node;
    }

    public String getId() {
        return id;
    }
}
