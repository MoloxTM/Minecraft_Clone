package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;

public interface CavesGenerator {

    void generateCaves(World world, Chunk chunk);

}
