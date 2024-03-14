package fr.math.minecraft.shared.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3i;

public class WorldLoader {

    public void load(World world, JsonNode worldData) {

        ArrayNode brokenBlocksArray = (ArrayNode) worldData.get("brokenBlocks");
        ArrayNode placedBlocksArray = (ArrayNode) worldData.get("placedBlocks");

        for (int i = 0; i < brokenBlocksArray.size(); i++) {
            JsonNode brokenBlockNode = brokenBlocksArray.get(i);

            int worldX = brokenBlockNode.get("wx").asInt();
            int worldY = brokenBlockNode.get("wy").asInt();
            int worldZ = brokenBlockNode.get("wz").asInt();
            byte block = (byte) brokenBlockNode.get("block").asInt();
            Vector3i worldPosition = new Vector3i(worldX, worldY, worldZ);
            BreakedBlock breakedBlock = new BreakedBlock(worldPosition, block);

            world.getBrokenBlocks().put(worldPosition, breakedBlock);

        }

        for (int i = 0; i < placedBlocksArray.size(); i++) {
            JsonNode placedBlockNode = placedBlocksArray.get(i);

            int worldX = placedBlockNode.get("wx").asInt();
            int worldY = placedBlockNode.get("wy").asInt();
            int worldZ = placedBlockNode.get("wz").asInt();
            int localX = placedBlockNode.get("lx").asInt();
            int localY = placedBlockNode.get("ly").asInt();
            int localZ = placedBlockNode.get("lz").asInt();

            byte block = (byte) placedBlockNode.get("block").asInt();
            String playerUuid = placedBlockNode.get("playerUuid").asText();
            Vector3i worldPosition = new Vector3i(worldX, worldY, worldZ);
            Vector3i localPosition = new Vector3i(localX, localY, localZ);

            PlacedBlock placedBlock = new PlacedBlock(playerUuid, worldPosition, localPosition, block);

            world.getPlacedBlocks().put(worldPosition, placedBlock);

        }

    }

}
