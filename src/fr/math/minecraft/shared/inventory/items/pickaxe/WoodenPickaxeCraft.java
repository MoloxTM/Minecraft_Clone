package fr.math.minecraft.shared.inventory.items.pickaxe;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class WoodenPickaxeCraft extends CraftRecipes {

    public WoodenPickaxeCraft() {
        super(new ItemStack(Material.WOODEN_PICKAXE, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data = new CraftData(new byte[]
                {
                        Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        craftingTable.add(data);
    }
}
