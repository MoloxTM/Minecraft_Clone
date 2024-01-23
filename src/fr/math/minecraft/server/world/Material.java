package fr.math.minecraft.server.world;

public enum Material {

    AIR("Air", -1, -1, -1),
    STONE("Stone", 0, 10, 15),
    DIRT("Dirt", 1, 2, 15),
    GRASS("Grass", 3, 3, 6),
    WATER("Water", 2, 15, 2);

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
