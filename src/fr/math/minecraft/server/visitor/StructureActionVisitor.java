package fr.math.minecraft.server.visitor;

import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;

public class StructureActionVisitor implements StructureVisitor{
    @Override
    public Void visit(PlainBiome biome) {
        return null;
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
