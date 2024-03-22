package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.network.PlayerInputData;

import static org.lwjgl.glfw.GLFW.*;

public class InventoryInputsHandler {

    public void handleInputs(long window, Player player, Inventory inventory, float mouseX, float mouseY) {

        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();

        float slotScaleX = inventoryWidth / 177.0f;
        float slotScaleY = inventoryHeight / 166.0f;
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
            float itemX = inventory.getItemX(i);
            float itemY = inventory.getItemY(i);

            if (itemX <= mouseX && mouseX <= itemX + slotSize * slotScaleX) {
                if (itemY <= mouseY && mouseY <= itemY + slotSize * slotScaleY) {
                    inventory.setCurrentSlot(i);
                    ItemStack item = inventory.getSelectedItem();
                    if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
                        if (player.canPlaceHoldedItem()) {

                            PlayerInputData playerInputData = new PlayerInputData(player.getLastInventory().getHoldedSlot(), player.getLastInventory().getType(), inventory.getType(), i);
                            ItemStack holdedItem = player.getLastInventory().getItems()[player.getLastInventory().getHoldedSlot()];

                            if (inventory.getItems()[i] == null) {
                                inventory.setItem(holdedItem, i);
                                player.getLastInventory().setItem(item, player.getLastInventory().getHoldedSlot());
                            } else {
                                if (inventory.getItems()[i].getMaterial() == holdedItem.getMaterial()) {
                                    holdedItem.setAmount(holdedItem.getAmount() + 1);
                                    inventory.setItem(holdedItem, i);
                                    player.getLastInventory().setItem(null, player.getLastInventory().getHoldedSlot());
                                } else {
                                    inventory.setItem(holdedItem, i);
                                    player.getLastInventory().setItem(item, player.getLastInventory().getHoldedSlot());
                                }
                            }

                            player.getInputs().add(playerInputData);
                            player.setCanHoldItem(false);
                            player.setCanPlaceHoldedItem(false);
                            player.getLastInventory().setHoldedSlot(-1);
                        } else if (inventory.getHoldedSlot() == -1 && player.canHoldItem() && item != null) {
                            inventory.setHoldedSlot(i);
                            player.setCanPlaceHoldedItem(false);
                            player.setLastInventory(inventory);
                        }
                    }
                    return;
                }
            }
        }

        inventory.setCurrentSlot(inventory.getItems().length);
    }
}
