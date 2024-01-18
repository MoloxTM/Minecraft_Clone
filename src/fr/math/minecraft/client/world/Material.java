package fr.math.minecraft.client.world;

public enum Material {

    AIR("Air", -1, -1, -1),
    STONE("Stone", 0, 1, 15),
    DIRT("Dirt", 1, 2, 15),
    GRASS("Grass", 3, 2, 14),
    WATER("Water", 2, 15, 2);

    private final int x;
    private final int y;
    private final int id;
    private final String name;

    Material(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Material getMaterialById(int id) {
        for (Material material : Material.values()) {
            if (id == material.getId()) {
                return material;
            }
        }
        return null;
    }
}
