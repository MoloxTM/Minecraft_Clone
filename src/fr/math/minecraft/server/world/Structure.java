package fr.math.minecraft.server.world;

import fr.math.minecraft.shared.world.Coordinates;
import org.joml.Vector3i;
import java.util.ArrayList;
import java.util.HashMap;

public class Structure {

    private HashMap<Coordinates, Byte> structureMap;
    private ArrayList<Coordinates> structures;
    private ArrayList<Vector3i> chunckVisited;
    public Structure() {
        this.structureMap = new HashMap<>();
        this.structures = new ArrayList<>();
        this.chunckVisited = new ArrayList<>();
    }

    public void setBlock(int worldX, int worldY, int worldZ, byte block) {
        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        this.structureMap.put(coordinates, block);
    }

    public HashMap<Coordinates, Byte> getStructureMap() {
        return structureMap;
    }

    public ArrayList<Coordinates> getStructures() {
        return structures;
    }

    public ArrayList<Vector3i> getChunckVisited() {
        return chunckVisited;
    }
}
