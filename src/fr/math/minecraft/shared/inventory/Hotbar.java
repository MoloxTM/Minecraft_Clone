package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.inventory.ItemStack;

public class Hotbar extends Inventory {

    public Hotbar() {
        super();
        this.items = new ItemStack[9];
    }

    public ItemStack[] getItems() {
        return items;
    }


}
