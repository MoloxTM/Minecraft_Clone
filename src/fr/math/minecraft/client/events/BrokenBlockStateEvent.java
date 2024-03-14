package fr.math.minecraft.client.events;

import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.PlacedBlock;
import fr.math.minecraft.shared.world.World;

public class BrokenBlockStateEvent {

    private final BreakedBlock breakedBlock;
    private final World world;

    public BrokenBlockStateEvent(World world, BreakedBlock breakedBlock) {
        this.world = world;
        this.breakedBlock = breakedBlock;
    }

    public World getWorld() {
        return world;
    }

    public BreakedBlock getBreakedBlock() {
        return breakedBlock;
    }
}
