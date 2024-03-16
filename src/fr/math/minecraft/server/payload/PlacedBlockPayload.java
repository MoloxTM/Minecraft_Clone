package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.world.PlacedBlock;

import java.net.DatagramPacket;

public class PlacedBlockPayload {

    private final PlacedBlock placedBlock;

    public PlacedBlockPayload(PlacedBlock placedBlock) {
        this.placedBlock = placedBlock;
    }

    public void send(Client client) {
        ObjectMapper mapper = new ObjectMapper();
        MinecraftServer server = MinecraftServer.getInstance();
        ObjectNode blockNode = placedBlock.toJSONObject();
        blockNode.put("type", "PLACED_BLOCK_STATE");
        try {
            byte[] buffer = mapper.writeValueAsBytes(blockNode);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            packet.setAddress(client.getAddress());
            packet.setPort(client.getPort());

            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
