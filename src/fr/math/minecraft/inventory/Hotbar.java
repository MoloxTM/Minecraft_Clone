package fr.math.minecraft.inventory;

public class Hotbar {

    private final ItemStack[] items;

    public Hotbar() {
        this.items = new ItemStack[9];
    }

    public ItemStack[] getItems() {
        return items;
    }
}
