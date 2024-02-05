package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.JsonNode;

public class ChunkPacketEvent {

    private final JsonNode chunkData;

    public ChunkPacketEvent(JsonNode chunkData) {
        this.chunkData = chunkData;
    }

    public JsonNode getChunkData() {
        return chunkData;
    }
}
