package fr.math.minecraft.server.world;

import java.util.HashMap;

public class Structure {

    private HashMap<Coordinates, Byte> structureMap;
    public Structure() {
        this.structureMap = new HashMap<>();
    }

    public void setBlock(int worldX, int worldY, int worldZ, byte block) {
        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        this.structureMap.put(coordinates, block);
    }

    public HashMap<Coordinates, Byte> getStructureMap() {
        return structureMap;
    }
}
