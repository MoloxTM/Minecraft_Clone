package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.inventory.Inventory;
import fr.math.minecraft.inventory.ItemStack;
import fr.math.minecraft.shared.GameConfiguration;

import static org.lwjgl.glfw.GLFW.*;

public class InventoryInputsHandler {

    public void handleInputs(long window, Player player, Inventory inventory, float mouseX, float mouseY) {

        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();

        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;

        float slotScaleX = inventoryWidth / 177.0f;
        float slotScaleY = inventoryHeight / 166.0f;
        float slotHeight = 18.0f * slotScaleY;
        float slotWidth = 18.0f * slotScaleX;
        float slotSize = 16.0f * 1.4f * gameConfiguration.getGuiScale();

        mouseY = GameConfiguration.WINDOW_HEIGHT - mouseY;

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
            if (inventory.getHoldedSlot() != -1) {
                player.setCanPlaceHoldedItem(true);
            } else if (!player.canHoldItem()) {
                player.setCanHoldItem(true);
            }
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            float itemX = 8.0f * slotScaleX + inventoryX + (i % 9) * slotWidth;
            float itemY = inventoryY + 12.0f * slotScaleY + (3 - (int) (i / 9.0f)) * slotHeight;

            if (itemX <= mouseX && mouseX <= itemX + slotSize * slotScaleX) {
                if (itemY <= mouseY && mouseY <= itemY + slotSize * slotScaleY) {
                    inventory.setCurrentSlot(i);
                    ItemStack item = inventory.getSelectedItem();
                    if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
                        if (player.canPlaceHoldedItem()) {
                            ItemStack holdedItem = inventory.getItems()[inventory.getHoldedSlot()];
                            inventory.setItem(holdedItem, i);
                            inventory.setItem(item, inventory.getHoldedSlot());
                            player.setCanHoldItem(false);
                            player.setCanPlaceHoldedItem(false);
                            inventory.setHoldedSlot(-1);
                        } else if (inventory.getHoldedSlot() == -1 && player.canHoldItem() && item != null) {
                            inventory.setHoldedSlot(i);
                            player.setCanPlaceHoldedItem(false);
                        }
                    }
                    return;
                }
            }
        }

        inventory.setCurrentSlot(inventory.getItems().length);

    }
}
