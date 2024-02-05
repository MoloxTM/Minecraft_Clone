package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.JsonNode;

public class PlayerJoinEvent {

    private final JsonNode playerData;

    public PlayerJoinEvent(JsonNode playerData) {
        this.playerData = playerData;
    }

    public JsonNode getPlayerData() {
        return playerData;
    }
}
