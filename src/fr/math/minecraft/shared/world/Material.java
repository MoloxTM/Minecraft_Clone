package fr.math.minecraft.shared.world;

import org.joml.Vector2i;

public enum Material {

    AIR("Air", -1, -1, -1, -1, -1, false, false),
    STONE("Stone", 0, 1, 15, 7, 8),
    DIRT("Dirt", 1, 2, 15, 8, 8),
    GRASS("Grass", 3, new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(3, 15), new Vector2i(8, 13), new Vector2i(2, 15), 9, 8),
    DEBUG("Debug", -2, 2, 1, -1, -1),
    WATER("Water", 2, 15, 2, -1, -1, false, false),
    SAND("Sand", 4, 2, 14, 4, 7),
    CACTUS("Cactus", 5, new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(6, 11), new Vector2i(5, 11), new Vector2i(7, 11), 7, 1),
    OAK_LOG("Log",6,new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(4, 14), new Vector2i(5, 14), new Vector2i(5, 14), 6, 7),
    OAK_LEAVES("Oak leaves", 7, 4, 12, 1, 0),
    WEED("Weed", 8, 7, 13, 24, 1, false, true),
    ROSE("Rose", 63, 12, 15, 23, 1, false, true),
    DEAD_BUSH("Dead bush", 10, 7, 12, 14, 3, false, true),
    BIRCH_LOG("Birch log", 64, new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(5, 8), new Vector2i(10, 7), new Vector2i(10, 7), 8, 7),
    BIRCH_LEAVES("Oak leaves", 12, 4, 7, 1, 0),
    SAND_STONE("Sand stone", 61, new Vector2i(0, 3), new Vector2i(0, 3), new Vector2i(0, 3), new Vector2i(0, 3), new Vector2i(0, 4), new Vector2i(0, 2), 1, 7),
    CUT_SANDSTONE("Cut sandstone", 60, new Vector2i(0, 1), new Vector2i(0, 1), new Vector2i(0, 1), new Vector2i(0, 1), new Vector2i(0, 4), new Vector2i(0, 4), 3, 7),
    SMOOTH_SANDSTONE("Smooth sandstone", 59, 0, 4, 0, 8),
    GLASS("Glass", 58, 1, 12, 9, 4),
    CRAFTING_TABLE("Crafting Table", 56, new Vector2i(11, 12), new Vector2i(11, 12), new Vector2i(12, 12), new Vector2i(12, 12), new Vector2i(11, 13), new Vector2i(4, 15), 2, 1),
    FURNACE("Furnace", 70, new Vector2i(13, 13), new Vector2i(13, 13), new Vector2i(13, 13), new Vector2i(12, 13), new Vector2i(14, 12), new Vector2i(14, 12), 1, 5),
    OAK_PLANKS("Oak planks", 13, 4, 15, 5, 8),
    COBBLESTONE("Cobblestone", 16, 0, 14, 0, 8),
    CHEST("Chest", 17, new Vector2i(10, 14), new Vector2i(10, 14), new Vector2i(11, 14), new Vector2i(11, 14), new Vector2i(10, 14), new Vector2i(10, 14), 4, 1),
    STICK("Stick", 18, -1, -1, 14, 3, false, true),
    WOODEN_PICKAXE("Wooden Pickaxe", 19, -1, -1, 13, 3, false, true),
    STONE_PICKAXE("Stone Pickaxe", 20, -1, -1, 18, 3, false, true),
    IRON_PICKAXE("Iron Pickaxe", 21, -1, -1, 22, 3, false, true),
    DIAMOND_PICKAXE("Diamond Pickaxe", 22, -1, -1, 25, 3, false, true),
    WOODEN_SHOVEL("Wooden Shovel", 23, -1, -1, 15, 3, false, true),
    WOODEN_AXE("Wooden Axe", 24, -1, -1, 16, 3, false, true),
    WOODEN_SWORD("Wooden Sword", 25, -1, -1, 2, 2, false, true),
    STONE_SHOVEL("Stone Shovel", 26, -1, -1, 19, 3, false, true),
    STONE_AXE("Stone Axe", 27, -1, -1, 20, 3, false, true),
    STONE_SWORD("Wooden Sword", 28, -1, -1, 3, 2, false, true),
    IRON_SHOVEL("Iron Shovel", 29, -1, -1, 23, 3, false, true),
    IRON_AXE("Iron Axe", 30, -1, -1, 24, 3, false, true),
    IRON_SWORD("Iron Sword", 31, -1, -1, 23, 4, false, true),
    DIAMOND_SHOVEL("Diamond Shovel", 32, -1, -1, 26, 3, false, true),
    DIAMOND_AXE("Diamond Axe", 33, -1, -1, 27, 4, false, true),
    DIAMOND_SWORD("Diamond Sword", 34, -1, -1, 4, 3, false, true),
    IRON_INGOT("Iron Ingot", 35, -1, -1, 31, 4, false, true),
    GOLD_INGOT("Gold Ingot", 36, -1, -1, 30, 4, false, true),
    DIAMOND("Diamond", 37, -1, -1, 2, 3, false, true),
    LEATHER("Leather", 38, -1, -1, 16, 2, false, true),
    COAL("Coal", 39, -1, -1, 17, 1, false, true),
    LEATHER_HELMET("Leather Helmet", 40, -1, -1, 15, 2, false, true),
    LEATHER_CHESSPLATE("Leather Chessplate", 41, -1, -1, 17, 2, false, true),
    LEATHER_PANTS("Leather Pants", 42, -1, -1, 18, 2, false, true),
    LEATHER_BOOTS("Leather Boots", 43, -1, -1, 19, 2, false, true),
    IRON_HELMET("Iron Helmet", 44, -1, -1, 20, 2, false, true),
    IRON_CHESSPLATE("Iron Chessplate", 45, -1, -1, 21, 2, false, true),
    IRON_PANTS("Iron Pants", 46, -1, -1, 22, 2, false, true),
    IRON_BOOTS("Iron Boots", 47, -1, -1, 23, 2, false, true),
    DIAMOND_HELMET("Diamond Helmet", 48, -1, -1, 24, 2, false, true),
    DIAMOND_CHESSPLATE("Diamond Chessplate", 49, -1, -1, 25, 2, false, true),
    DIAMOND_PANTS("Diamond Pants", 50, -1, -1, 26, 2, false, true),
    DIAMOND_BOOTS("Diamond Boots", 51, -1, -1, 27, 2, false, true),
    SPRUCE_WOOD("Spruce Wood", 52, new Vector2i(4, 8), new Vector2i(4, 8), new Vector2i(4, 8), new Vector2i(4, 8), new Vector2i(5, 14), new Vector2i(5, 14), 7, 7),
    SPRUCE_LEAVES("Spruce leaves", 53, 5, 7, 0, 0),
    APPLE("Apple", 54, 4, 4, 21, 1, false, true, true),
    BREAD("Bread", 80, -1, -1, 19, 1, false, true, true),
    SNOW("Snow", 55, 2, 11, 8, 4, true, false),
    DIAMOND_ORE("Diamond Ore", 91, 2, 12, 0, 4),
    BREAKING_ANIMATION("", -3, 0, 0, 0, 0);

    private final int blockIconX, x;
    private final int blockIconY, y;
    private final byte id;
    private final String name;
    private final boolean faces, solid, item, food;
    private final Vector2i px, nx, pz, nz, py, ny;

    Material(String name, int id, int x, int y, int blockIconX, int blockIconY) {
        this(name, id, x, y, blockIconX, blockIconY, true, false, false);
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
        this.food = false;
    }

    Material(String name, int id, int x, int y, int blockIconX, int blockIconY, boolean solid, boolean item, boolean food) {
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
        this.food = food;
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
        this.food = false;
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
        return Material.DEBUG;
    }

    public static Material getMiningBlock(Material material) {
        switch (material) {
            case STONE:
                return Material.COBBLESTONE;
            case GRASS:
                return Material.DIRT;
            case OAK_LEAVES:
            case BIRCH_LEAVES:
            case SPRUCE_LEAVES:
                return Material.APPLE;
            case DEAD_BUSH:
                return Material.STICK;
            case WEED:
                return Material.AIR;
        }
        return material;
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

    public boolean isFood() {
        return food;
    }
}
