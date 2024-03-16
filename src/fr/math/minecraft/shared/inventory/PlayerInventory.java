package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.GameConfiguration;

public class PlayerInventory extends Inventory {

    public PlayerInventory() {
        super();
        this.items = new ItemStack[GameConfiguration.PLAYER_INVENTORY_SIZE];
    }

}
