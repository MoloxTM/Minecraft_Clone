package fr.math.minecraft.inventory;

import fr.math.minecraft.shared.world.Material;

public abstract class Inventory {

    protected ItemStack[] items;
    protected int slotIndex;
    protected int currentSize;
    protected int currentSlot;
    protected boolean open;
    protected ItemStack selectedItem;

    public Inventory() {
        this.slotIndex = 0;
        this.currentSize = 0;
        this.currentSlot = 0;
        this.open = false;
        this.selectedItem = null;
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

    public ItemStack getSelectedItem() {
        return this.selectedItem;
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setSelectedItem(ItemStack selectedItem) {
        this.selectedItem = selectedItem;
    }
}
