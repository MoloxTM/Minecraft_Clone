package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.world.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemStack {

    private int amount;
    private final Material material;
    private final List<String> lore;
    private boolean hovered;

    public ItemStack(Material material, int amount) {
        this.amount = amount;
        this.material = material;
        this.lore = new ArrayList<>();
    }

    public List<String> getLore() {
        return lore;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = (int) Math.min(amount, 64.0f);
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

}