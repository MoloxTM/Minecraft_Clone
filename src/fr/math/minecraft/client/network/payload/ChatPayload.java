package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.network.packet.ChatMessagePacket;

public class ChatPayload {

    private boolean open;
    private final StringBuilder message;
    private final Player sender;

    public ChatPayload(Player player) {
        this.sender = player;
        this.open = false;
        this.message = new StringBuilder();
    }

    public void send() {
        if (message.length() == 0) {
            return;
        }

        ChatMessagePacket packet = new ChatMessagePacket(sender, message.toString());
        packet.send();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public StringBuilder getMessage() {
        return message;
    }
}
