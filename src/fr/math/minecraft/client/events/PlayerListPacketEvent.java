package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class PlayerListPacketEvent {

    private final ArrayNode players;
    private final int tick;

    public PlayerListPacketEvent(int tick, ArrayNode players) {
        this.tick = tick;
        this.players = players;
    }

    public ArrayNode getPlayers() {
        return players;
    }

    public int getTick() {
        return tick;
    }
}
