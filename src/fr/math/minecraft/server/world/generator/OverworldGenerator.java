package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.Material;

public class OverworldGenerator implements TerrainGenerator {

    private final NoiseGenerator noise;

    public OverworldGenerator() {
        this.noise = new NoiseGenerator(6, 105.0f, 205.0f, .58f, 18);
    }

    @Override
    public void generate(ServerChunk chunk) {
        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int z = 0; z < ServerChunk.SIZE; z++) {

                int worldX = x + chunk.getPosition().x * ServerChunk.SIZE;
                int worldZ = z + chunk.getPosition().z * ServerChunk.SIZE;

                float worldNoise = noise.getNoise(worldX, worldZ);
                int worldHeight = (int) (worldNoise * noise.getAmplitude() + noise.getOffset());

                for (int y = 0; y < ServerChunk.SIZE; y++) {
                    int worldY = y + chunk.getPosition().y * ServerChunk.SIZE;
                    Material material = Material.AIR;

                    if (worldY < worldHeight) {
                        material = Material.STONE;
                    }

                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }
    }

}
