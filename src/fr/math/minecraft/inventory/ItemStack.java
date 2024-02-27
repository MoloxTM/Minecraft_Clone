package fr.math.minecraft.inventory;

import fr.math.minecraft.shared.world.Material;

public class ItemStack {

    private final int amount;
    private final Material material;

    public ItemStack(Material material, int amount) {
        this.amount = amount;
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }
}
