package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Material;

public class CraftingTableInventory extends Inventory {

    public CraftingTableInventory() {
        super();
        this.items = new ItemStack[9];
        this.type = InventoryType.CRAFTING_TABLE;
    }

    @Override
    public float getItemX(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleX = inventoryWidth / 177.0f;
        float slotWidth = 18.0f * slotScaleX;
        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        return 30.0f * slotScaleX + inventoryX + (slot % 3) * slotWidth;
    }

    @Override
    public float getItemY(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleY = inventoryHeight / 166.0f;
        float slotHeight = 18.0f * slotScaleY;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;
        return inventoryY + 12.0f * slotScaleY + 3 * slotHeight + 14.0f * slotScaleY + (3 - (int) (slot / 3.0f)) * slotHeight;
    }

}
