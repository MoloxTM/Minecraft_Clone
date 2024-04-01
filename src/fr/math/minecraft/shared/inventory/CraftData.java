package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.world.Material;

import java.util.Arrays;

public class CraftData {

    private byte[] tabCraft;

    public CraftData(byte[] tabCraft) {
        this.tabCraft = tabCraft;
    }

    public byte[] getTabCraft() {
        return tabCraft;
    }

    public void setTabCraft(byte[] tabCraft) {
        this.tabCraft = tabCraft;
    }

    public boolean equals(PlayerCraftInventory playerCraftInventory) {
        byte[] playerCraft = new byte[playerCraftInventory.getItems().length];
        for (int i = 0; i < playerCraftInventory.getItems().length; i++) {
            ItemStack itemStack = playerCraftInventory.getItems()[i];
            if (itemStack == null) {
                playerCraft[i] = Material.AIR.getId();
            } else {
                playerCraft[i] = itemStack.getMaterial().getId();
            }
        }
        return Arrays.equals(tabCraft, playerCraft);
    }

    public boolean equals(PlayerCraftingTableInventory playerCraftingTableInventory) {
        byte[] playerCraft = new byte[playerCraftingTableInventory.getItems().length];
        for (int i = 0; i < playerCraftingTableInventory.getItems().length; i++) {
            ItemStack itemStack = playerCraftingTableInventory.getItems()[i];
            if (itemStack == null) {
                playerCraft[i] = Material.AIR.getId();
            } else {
                playerCraft[i] = itemStack.getMaterial().getId();
            }
        }
        return Arrays.equals(tabCraft, playerCraft);
    }

}
