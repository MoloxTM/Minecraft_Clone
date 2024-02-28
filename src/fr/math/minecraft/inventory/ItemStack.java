package fr.math.minecraft.inventory;

import fr.math.minecraft.shared.world.Material;
import org.joml.Vector2f;

public class ItemStack {

    private int amount;
    private final Material material;

    public ItemStack(Material material, int amount) {
        this.amount = amount;
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public Vector2f[] calculateTexCoords() {

        float blocksIconsImageWidth = 256.0f;
        float blocksIconsSize = 48.0f;

        return new Vector2f[] {
            new Vector2f(material.getBlockIconX() / blocksIconsImageWidth, material.getBlockIconY() / blocksIconsImageWidth),
            new Vector2f(material.getBlockIconX() / blocksIconsImageWidth, (material.getBlockIconY() + blocksIconsSize) / blocksIconsImageWidth),
            new Vector2f((material.getBlockIconX() + blocksIconsSize) / blocksIconsImageWidth, (material.getBlockIconY() + blocksIconsSize) / blocksIconsImageWidth),
            new Vector2f((material.getBlockIconX() + blocksIconsSize) / blocksIconsImageWidth, material.getBlockIconY() / blocksIconsImageWidth),
        };
    }

}
