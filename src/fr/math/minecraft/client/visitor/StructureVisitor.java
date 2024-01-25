package fr.math.minecraft.client.visitor;

import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;

public interface StructureVisitor<T> {

    T visit(PlainBiome biome);
    T visit(ForestBiome biome);
    T visit(DesertBiome biome);
}
