package fr.math.minecraft.shared.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public abstract class Inventory {

    protected ItemStack[] items;
    protected int slotIndex;
    protected int currentSize;
    protected int currentSlot;
    protected boolean open;
    protected int holdedSlot;
    protected InventoryType type;

    public Inventory() {
        this.slotIndex = 0;
        this.currentSize = 0;
        this.currentSlot = 0;
        this.holdedSlot = -1;
        this.open = false;
        this.type = null;
    }

    public ItemStack[] getItems() {
        return items;
    }
    public ItemStack getItemAtSlot(int slot) {
        return items[slot];
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public ItemStack getSelectedItem() {

        if (currentSlot < items.length) {
            return items[currentSlot];
        }

        return null;
    }

    public int getItemIndex(ItemStack itemStack) {
        return this.getItemIndex(itemStack.getMaterial());
    }

    public int getItemIndex(Material material) {
        int index = -1;
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];

            if (item == null) {
                continue;
            }

            if (item.getMaterial().getId() == material.getId()) {
                return i;
            }
        }
        return index;
    }

    public int getAvailableSlot() {
        for (int slot = 0; slot < items.length; slot++) {
            if (items[slot] == null) {
                return slot;
            }
        }
        return -1;
    }

    public void addItem(ItemStack item) {
        int availableSlot = this.getAvailableSlot();
        if (availableSlot == -1) {
            return;
        }
        int itemIndex = this.getItemIndex(item);
        if (itemIndex == -1) {
            items[availableSlot] = new ItemStack(item);
            currentSize++;
            return;
        }
        ItemStack currentItem = items[itemIndex];
        currentItem.setAmount(currentItem.getAmount() + item.getAmount());
    }

    public void removeItem(Material material) {
        int itemSlot = this.getItemIndex(material);
        System.out.println("item slot ?? " + itemSlot);
        if (itemSlot == -1) {
            return;
        }
        ItemStack item = items[itemSlot];
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() == 0) {
            this.setItem(null, itemSlot);
        }

    }

    public void setItem(ItemStack item, int slot) {
        items[slot] = item;
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

    public int getSize() {
        return items.length;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getHoldedSlot() {
        return holdedSlot;
    }

    public void setHoldedSlot(int holdedSlot) {
        this.holdedSlot = holdedSlot;
    }

    public boolean isFull() {
        return currentSize >= this.getSize();
    }

    public abstract float getItemX(int slot);
    public abstract float getItemY(int slot);

    public ArrayNode toJSONArray() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int slot = 0; slot < items.length; slot++) {
            ItemStack item = items[slot];
            ObjectNode itemNode = mapper.createObjectNode();

            Material material = Material.AIR;
            int amount = 0;

            if (item != null) {
                material = item.getMaterial();
                amount = item.getAmount();
            }

            itemNode.put("slot", slot);
            itemNode.put("block", material.getId());
            itemNode.put("amount", amount);

            arrayNode.add(itemNode);

        }

        return arrayNode;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public void clear() {
        for (int slot = 0; slot < items.length; slot++) {
            items[slot] = null;
        }
    }
}
