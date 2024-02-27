package fr.math.minecraft.inventory;

public abstract class Inventory {

    protected ItemStack[] items;
    protected int slotIndex;

    public Inventory() {
        this.slotIndex = 0;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }
}
