package fr.math.minecraft.client.events;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;

public class DroppedItemEvent {

    private final World world;
    private final ArrayNode itemsData;

    public DroppedItemEvent(World world, ArrayNode itemsData) {
        this.world = world;
        this.itemsData = itemsData;
    }

    public World getWorld() {
        return world;
    }

    public ArrayNode getItemsData() {
        return itemsData;
    }
}
