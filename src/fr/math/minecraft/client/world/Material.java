package fr.math.minecraft.client.world;

import org.joml.Vector2f;
import org.joml.Vector2i;

public enum Material {

    AIR("Air", -1, -1, -1),
    STONE("Stone", 0, 1, 15),
    DIRT("Dirt", 1, 2, 15),
    GRASS("Grass", 3, new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(8, 13), new Vector2i(2, 15)),
    DEBUG("Debug", -2, 0, 1),
    WATER("Water", 2, 15, 2),
    SAND("Sand",4,2,14),
    CACTUS("Cactus",5,new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(5, 11), new Vector2i(7, 11)),
    OAK_LOG("Log",6,new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(5, 14), new Vector2i(5, 14)),
    OAK_LEAVES("Oak leaves",7,4,12);


    private final int x;
    private final int y;
    private final byte id;
    private final String name;
    private final boolean faces;

    private final Vector2i px, nx, pz, nz, py, ny;

    Material(String name, int id, int x, int y) {
        this.name = name;
        this.id = (byte)id;
        this.x = x;
        this.y = y;
        this.px = null;
        this.nx = null;
        this.pz = null;
        this.nz = null;
        this.py = null;
        this.ny = null;
        this.faces = false;
    }
    Material(String name, int id, Vector2i px, Vector2i nx, Vector2i pz, Vector2i nz, Vector2i py, Vector2i ny) {
        this.name = name;
        this.id = (byte)id;
        this.x = -1;
        this.y = -1;
        this.faces = true;
        this.px = px;
        this.nx = nx;
        this.pz = pz;
        this.nz = nz;
        this.py = py;
        this.ny = ny;
    }

    public String getName() {
        return name;
    }

    public byte getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFaces() {
        return faces;
    }

    public Vector2i getPx() {
        return px;
    }

    public Vector2i getNx() {
        return nx;
    }

    public Vector2i getPz() {
        return pz;
    }

    public Vector2i getNz() {
        return nz;
    }

    public Vector2i getPy() {
        return py;
    }

    public Vector2i getNy() {
        return ny;
    }

    public static Material getMaterialById(byte id) {
        for (Material material : Material.values()) {
            if (id == material.getId()) {
                return material;
            }
        }
        return null;
    }
}
