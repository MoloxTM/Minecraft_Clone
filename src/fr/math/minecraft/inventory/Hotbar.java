package fr.math.minecraft.inventory;

public class Hotbar extends Inventory {

    public Hotbar() {
        super();
        this.items = new ItemStack[9];
    }

    public ItemStack[] getItems() {
        return items;
    }


}
