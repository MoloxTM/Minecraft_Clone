package fr.math.minecraft.shared.crafts;

import java.util.ArrayList;

public abstract class CraftRecipes {

    protected ArrayList<CraftData> playerInventory;
    protected ArrayList<CraftData> craftingTable;

    public CraftRecipes() {
        this.playerInventory = new ArrayList<>();
        this.craftingTable = new ArrayList<>();
    }

    public abstract void fillRecipe();
}
