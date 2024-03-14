package fr.math.minecraft.client.events;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.world.PlacedBlock;
import fr.math.minecraft.shared.world.World;

public class PlacedBlockStateEvent {

    private final PlacedBlock placedBlock;
    private final World world;

    public PlacedBlockStateEvent(World world, PlacedBlock placedBlock) {
        this.world = world;
        this.placedBlock = placedBlock;
    }

    public World getWorld() {
        return world;
    }

    public PlacedBlock getPlacedBlock() {
        return placedBlock;
    }
}
