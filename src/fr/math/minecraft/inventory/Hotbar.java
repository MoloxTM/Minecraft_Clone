package fr.math.minecraft.inventory;

public class Hotbar {

    private final ItemStack[] items;
    private int currentSlot;

    public Hotbar() {
        this.items = new ItemStack[9];
        this.currentSlot = 0;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
    }
}
