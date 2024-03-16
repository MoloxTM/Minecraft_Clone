package fr.math.minecraft.shared.inventory.items;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class ChestCraft extends CraftRecipes {

    public ChestCraft() {
        super(new ItemStack(Material.CHEST, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData dataCraftingTable = new CraftData(new byte[]
                {
                        Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(),
                        Material.OAK_PLANKS.getId(), Material.AIR.getId(), Material.OAK_PLANKS.getId(),
                        Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(),
                }
        );

        craftingTable.add(dataCraftingTable);
    }
}
