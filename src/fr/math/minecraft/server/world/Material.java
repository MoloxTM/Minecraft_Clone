package fr.math.minecraft.server.world;

public enum Material {

    AIR("Air", -1, -1, -1),
    STONE("Stone", 0, 10, 15),
    DIRT("Dirt", 1, 2, 15),
    WATER("Water", 2, 15, 2),
    GRASS("Grass", 3, 3, 6),
    SAND("Sand",4,2,14),
    CACTUS("Cactus",5,5,7),
    OAK_LOG("Oak log",6,4,14),
    OAK_LEAVES("Oak leaves",7,4,12),
    WEED("Weed", 8, 7, 13),
    ROSE("Rose", 9, 12, 15),
    DEAD_BUSH("Dead bush", 10, 7, 12),
    BIRCH_LOG("Birch log",11, 10, 8),
    BIRCH_LEAVES("Oak leaves",12,4,7);


    private final int x;
    private final int y;
    private final byte id;
    private final String name;

    Material(String name, int id, int x, int y) {
        this.name = name;
        this.id = (byte)id;
        this.x = x;
        this.y = y;
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
}
