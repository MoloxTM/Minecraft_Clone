package fr.math.minecraft.shared.crafts;

import fr.math.minecraft.shared.world.Material;

public class CraftData {

    private int slot;
    private Material material;

    public CraftData(int slot, Material material) {
        this.slot = slot;
        this.material = material;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
