package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class PlayerListPacketEvent {

    private final ArrayNode players;

    public PlayerListPacketEvent(ArrayNode players) {
        this.players = players;
    }

    public ArrayNode getPlayers() {
        return players;
    }
}
