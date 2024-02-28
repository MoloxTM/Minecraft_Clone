package fr.math.minecraft.inventory;

import fr.math.minecraft.shared.world.Material;

public abstract class Inventory {

    protected ItemStack[] items;
    protected int slotIndex;
    protected int currentSize;

    public Inventory() {
        this.slotIndex = 0;
        this.currentSize = 0;
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

    public int getItemIndex(ItemStack itemStack) {
        return this.getItemIndex(itemStack.getMaterial());
    }

    public int getItemIndex(Material material) {
        int index = -1;
        for (int i = 0; i < currentSize; i++) {
            ItemStack item = items[i];

            if (item.getMaterial().getId() == material.getId()) {
                return i;
            }
        }
        return index;
    }

    public void addItem(ItemStack item) {
        int itemIndex = this.getItemIndex(item);
        if (itemIndex == -1) {
            items[currentSize] = item;
            currentSize++;
            return;
        }
        ItemStack currentItem = items[itemIndex];
        currentItem.setAmount(currentItem.getAmount() + 1);
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
