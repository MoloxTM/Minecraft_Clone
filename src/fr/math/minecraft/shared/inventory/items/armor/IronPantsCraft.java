package fr.math.minecraft.shared.inventory.items.armor;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class IronPantsCraft extends CraftRecipes {

    public IronPantsCraft() {
        super(new ItemStack(Material.IRON_PANTS, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data1 = new CraftData(new byte[]
                {
                        Material.IRON_INGOT.getId(), Material.IRON_INGOT.getId(), Material.IRON_INGOT.getId(),
                        Material.IRON_INGOT.getId(), Material.AIR.getId(), Material.IRON_INGOT.getId(),
                        Material.IRON_INGOT.getId(), Material.AIR.getId(), Material.IRON_INGOT.getId()
                }
        );

        craftingTable.add(data1);

    }
}
