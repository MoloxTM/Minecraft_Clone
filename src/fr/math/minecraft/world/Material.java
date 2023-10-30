package fr.math.minecraft.world;

public enum Material {

    AIR("Air", -1, -1, -1),
    STONE("Stone", 0, 10, 15),
    DIRT("Dirt", 1, 2, 15);

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
}
