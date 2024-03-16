package fr.math.minecraft.shared.inventory;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftController {

    private ArrayList<CraftRecipes> recipes;

    public CraftController() {
        this.recipes = new ArrayList<>();
    }

    public ArrayList<CraftRecipes> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<CraftRecipes> recipes) {
        this.recipes = recipes;
    }

    public void registerCraft(CraftRecipes craftRecipes) {
        recipes.add(craftRecipes);
    }

    public ItemStack getCraft(PlayerCraftInventory playerCraftInventory) {
        for (CraftRecipes craftRecipes : recipes) {
            for (int i = 0; i < craftRecipes.getPlayerInventory().size(); i++) {
                CraftData craftData = craftRecipes.getPlayerInventory().get(i);
                System.out.println("Tableau de la recette " + i + " : " + Arrays.toString(craftData.getTabCraft()));
                if (craftData.equals(playerCraftInventory)) {
                    return craftRecipes.getCraft();
                }
            }
        }
        return null;
    }
}
