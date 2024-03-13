package fr.math.minecraft.client.events;

import fr.math.minecraft.shared.inventory.ItemStack;

public class ItemGiveEvent {

    private final ItemStack item;
    private final String droppedItemId;

    public ItemGiveEvent(String droppedItemId, ItemStack item) {
        this.item = item;
        this.droppedItemId = droppedItemId;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getDroppedItemId() {
        return droppedItemId;
    }
}
