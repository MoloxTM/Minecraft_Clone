package fr.math.minecraft.shared.inventory.items.pickaxe;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class StonePickaxeCraft extends CraftRecipes {

    public StonePickaxeCraft() {
        super(new ItemStack(Material.STONE_PICKAXE, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data = new CraftData(new byte[]
                {
                        Material.COBBLESTONE.getId(), Material.COBBLESTONE.getId(), Material.COBBLESTONE.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        craftingTable.add(data);
    }
}
