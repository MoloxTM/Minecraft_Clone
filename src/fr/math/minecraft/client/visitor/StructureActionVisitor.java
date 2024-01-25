package fr.math.minecraft.client.visitor;

import fr.math.minecraft.client.builder.StructureBuilder;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;

public class StructureActionVisitor implements StructureVisitor{
    @Override
    public Void visit(PlainBiome biome, ServerChunk chunk) {
        StructureBuilder.buildSimpleTree(chunk);
    }

    @Override
    public Void visit(ForestBiome biome) {
        return null;
    }

    @Override
    public Void visit(DesertBiome biome) {
        return null;
    }
}
