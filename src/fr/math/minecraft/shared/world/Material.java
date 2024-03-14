package fr.math.minecraft.shared.world;

import org.joml.Vector2i;

public enum Material {

    AIR("Air", -1, -1, -1, -1, -1, false, false),
    STONE("Stone", 0, 1, 15, 7, 8),
    DIRT("Dirt", 1, 2, 15, 8, 8),
    GRASS("Grass", 3, new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(8, 13), new Vector2i(2, 15), 9, 8),
    DEBUG("Debug", -2, 0, 1, -1, -1),
    WATER("Water", 2, 15, 2, -1, -1, false, false),
    SAND("Sand", 4, 2, 14, 4, 7),
    CACTUS("Cactus", 5, new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(5, 11), new Vector2i(7, 11), 7, 1),
    OAK_LOG("Log",6,new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(5, 14), new Vector2i(5, 14), 6, 7),
    OAK_LEAVES("Oak leaves", 7, 4, 12, 1, 0),
    WEED("Weed", 8, 7, 13, -1, -1),
    ROSE("Rose", 9, 12, 15, -1, -1),
    DEAD_BUSH("Dead bush", 10, 7, 12, -1, -1),
    BIRCH_LOG("Birch log", 11, new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(10, 7), new Vector2i(10, 7), 8, 7),
    BIRCH_LEAVES("Oak leaves", 12, 4, 7, 0, 0),
    SPRUCE_WOOD("Spruce Wood", 13, 4, 8, 0, 0),
    SPRUCE_LEAVES("Spruce leaves", 14, 5, 7, 0, 0),
    APPLE("Apple", 15, -1, -1, 21, 1, false, true),
    SNOW("Snow", 16, 2, 11, -1, -1, true, false),
    DIAMOND_SWORD("Diamond Sword", 17, -1, -1, 4, 2, false, true),
    DIAMOND_AXE("Diamond Axe", 18, -1, -1, 27, 3, false, true),
    BREAKING_ANIMATION("", -3, 0, 0, 0, 0);

    private final int blockIconX, x;
    private final int blockIconY, y;
    private final byte id;
    private final String name;
    private final boolean faces, solid, item;
    private final Vector2i px, nx, pz, nz, py, ny;

    Material(String name, int id, int x, int y, int blockIconX, int blockIconY) {
        this(name, id, x, y, blockIconX, blockIconY, true, false);
    }

    Material(String name, int id, int x, int y, int blockIconX, int blockIconY, boolean solid, boolean item) {
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
        this.blockIconX = blockIconX;
        this.blockIconY = blockIconY;
        this.faces = false;
        this.solid = solid;
        this.item = item;
    }

    Material(String name, int id, Vector2i px, Vector2i nx, Vector2i pz, Vector2i nz, Vector2i py, Vector2i ny, int blockIconX, int blockIconY) {
        this(name, id, px, nx, pz, nz, py, ny, blockIconX, blockIconY, true, false);
    }
    Material(String name, int id, Vector2i px, Vector2i nx, Vector2i pz, Vector2i nz, Vector2i py, Vector2i ny, int blockIconX, int blockIconY, boolean solid, boolean item) {
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
        this.blockIconX = blockIconX;
        this.blockIconY = blockIconY;
        this.solid = solid;
        this.item = item;
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

    public boolean isSymetric() {
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

    public boolean isSolid() {
        return solid;
    }

    public int getBlockIconX() {
        return blockIconX;
    }

    public int getBlockIconY() {
        return blockIconY;
    }

    public boolean isItem() {
        return item;
    }
}
