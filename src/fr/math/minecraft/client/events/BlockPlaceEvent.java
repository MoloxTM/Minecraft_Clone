package fr.math.minecraft.client.events;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.world.Material;
import org.joml.Vector3i;

public class BlockPlaceEvent {
    private final Player player;
    private final Vector3i position;
    private final Material material;

    public BlockPlaceEvent(Player player, Vector3i position, Material material) {
        this.player = player;
        this.position = position;
        this.material = material;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector3i getPosition() {
        return position;
    }

    public Material getMaterial() {
        return material;
    }
}
