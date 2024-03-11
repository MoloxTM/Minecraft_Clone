package fr.math.minecraft.inventory;

import fr.math.minecraft.shared.world.Material;
import org.joml.Math;
import org.joml.Vector2f;

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
