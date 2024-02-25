package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;

public class OverworldCavesGenerator implements CavesGenerator {


    @Override
    public void generateCaves(World world, Chunk chunk) {
        PerlinNoiseGenerator noiseGenerator = new PerlinNoiseGenerator(1, 0.5f, 3, 2.175f, 0);
        OverworldGenerator overworldGenerator = new OverworldGenerator();


    }

}
