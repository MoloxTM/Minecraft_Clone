package fr.math.minecraft.client.events;

import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;

public class DroppedItemEvent {

    private final String itemUuid;
    private final Material material;

    private final Vector3f position;
    private final World world;

    public DroppedItemEvent(World world, String itemUuid, Material material, Vector3f position) {
        this.itemUuid = itemUuid;
        this.material = material;
        this.position = position;
        this.world = world;
    }

    public String getItemUuid() {
        return itemUuid;
    }

    public Material getMaterial() {
        return material;
    }

    public Vector3f getPosition() {
        return position;
    }

    public World getWorld() {
        return world;
    }
}
