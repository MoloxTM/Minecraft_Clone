package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.GameConfiguration;

public class PlayerCraftInventory extends Inventory {

    public PlayerCraftInventory() {
        super();
        this.type = InventoryType.CRAFT_INVENTORY;
        this.items = new ItemStack[4];
    }

    @Override
    public float getItemX(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleX = inventoryWidth / 177.0f;
        float slotWidth = 18.0f * slotScaleX;
        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        return 87.0f * slotScaleX + inventoryX + (slot % 2) * slotWidth;
    }

    @Override
    public float getItemY(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleY = inventoryHeight / 166.0f;
        float slotHeight = 18.0f * slotScaleY;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;
        return inventoryY + 12.0f * slotScaleY + 3 * slotHeight + 22.0f * slotScaleY + (2 - (int) (slot / 2.0f)) * slotHeight;
    }


}
