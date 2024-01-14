package fr.math.minecraft.client.world.generator;

import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Material;
import fr.math.minecraft.client.world.World;
import org.joml.Vector2f;

public class OverworldGenerator implements TerrainGenerator {

    private final NoiseGenerator noise;

    public OverworldGenerator() {
        this.noise = new NoiseGenerator(6, 105.0f, 205.0f, .58f, 18);
    }

    @Override
    public void generate(Chunk chunk) {
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {

                int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                float worldNoise = noise.getNoise(worldX, worldZ);
                int worldHeight = (int) (worldNoise * noise.getAmplitude() + noise.getOffset());
                for (int y = 0; y < Chunk.SIZE; y++) {
                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;
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
