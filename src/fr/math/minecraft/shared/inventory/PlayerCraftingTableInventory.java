package fr.math.minecraft.shared.inventory;

public class PlayerCraftingTableInventory extends Inventory{

    public PlayerCraftingTableInventory() {
        super();
        this.items = new ItemStack[9];
    }

    @Override
    public ItemStack[] getItems() {
        return super.getItems();
    }

    @Override
    public float getItemX(int slot) {
        return 0;
    }

    @Override
    public float getItemY(int slot) {
        return 0;
    }
}
