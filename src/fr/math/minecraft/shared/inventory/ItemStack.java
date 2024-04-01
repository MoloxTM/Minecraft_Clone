package fr.math.minecraft.shared.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public ItemStack(JsonNode itemNode) {
        this.amount = itemNode.get("amount").asInt();
        this.material = Material.getMaterialById((byte) itemNode.get("block").asInt());
        this.lore = new ArrayList<>();
    }

    public ItemStack(ItemStack item) {
        this.amount = item.getAmount();
        this.material = item.getMaterial();
        this.lore = new ArrayList<>(item.getLore());
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

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode itemNode = mapper.createObjectNode();

        itemNode.put("block", material.getId());
        itemNode.put("amount", amount);

        return itemNode;
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "amount=" + amount +
                ", material=" + material +
                ", lore=" + lore +
                ", hovered=" + hovered +
                '}';
    }
}
