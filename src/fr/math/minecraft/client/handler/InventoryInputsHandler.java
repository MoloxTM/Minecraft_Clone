package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.inventory.Inventory;
import fr.math.minecraft.inventory.ItemStack;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Material;

public class InventoryInputsHandler {

    public void handleInputs(Player player, Inventory inventory, float mouseX, float mouseY) {

        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH;
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT;

        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;

        float scale = 1.4f;
        float slotSize = 16.0f;

        mouseY = GameConfiguration.WINDOW_HEIGHT - mouseY;

        inventory.setSelectedItem(null);

        for (int i = 0; i < inventory.getCurrentSize(); i++) {
            ItemStack item = inventory.getItems()[i];
            Material material = item.getMaterial();

            float itemX = 7.0f + inventoryX + i * slotSize * (scale + 0.5f);
            float itemY = inventoryY + 7.0f + 4.0f + slotSize * 4 * scale;

            if (itemX <= mouseX && mouseX <= itemX + slotSize * scale) {
                if (itemY <= mouseY && mouseY <= itemY + slotSize * scale) {
                    System.out.println("Item : " + material);
                    inventory.setSelectedItem(item);
                }
            }
        }
    }
}
