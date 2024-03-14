package fr.math.minecraft.server.saver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.PlacedBlock;
import fr.math.minecraft.shared.world.World;

import java.io.File;
import java.io.IOException;

public class WorldSaver {

    private final ObjectMapper mapper;
    private final static String SAVES_FOLDER = "saves/";

    public WorldSaver() {
        this.mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public void save(World world, Client client) throws IOException {

        JsonNode clientNode = client.toJSON();
        File file = new File(SAVES_FOLDER + "players/" + client.getUuid() + ".json");

        mapper.writeValue(file, clientNode);
    }

    public void save(World world) throws IOException {

        File file = new File(SAVES_FOLDER + "world.json");

        ObjectNode worldNode = mapper.createObjectNode();
        ArrayNode brokenBlocksArray = mapper.createArrayNode();
        ArrayNode placedBlocksArray = mapper.createArrayNode();

        for (BreakedBlock breakedBlock : world.getBrokenBlocks().values()) {
            brokenBlocksArray.add(breakedBlock.toJSONObject());
        }

        for (PlacedBlock placedBlock : world.getPlacedBlocks().values()) {
            brokenBlocksArray.add(placedBlock.toJSONObject());
        }

        worldNode.set("brokenBlocks", brokenBlocksArray);
        worldNode.set("placedBlocksArray", placedBlocksArray);

        mapper.writeValue(file, worldNode);
    }

}