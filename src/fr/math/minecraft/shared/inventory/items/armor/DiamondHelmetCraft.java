package fr.math.minecraft.shared.inventory.items.armor;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class DiamondHelmetCraft extends CraftRecipes {

    public DiamondHelmetCraft() {
        super(new ItemStack(Material.DIAMOND_HELMET, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data1 = new CraftData(new byte[]
                {
                        Material.DIAMOND.getId(), Material.DIAMOND.getId(), Material.DIAMOND.getId(),
                        Material.DIAMOND.getId(), Material.AIR.getId(), Material.DIAMOND.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData data2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.DIAMOND.getId(), Material.DIAMOND.getId(), Material.DIAMOND.getId(),
                        Material.DIAMOND.getId(), Material.AIR.getId(), Material.DIAMOND.getId()
                }
        );

        craftingTable.add(data1);
        craftingTable.add(data2);

    }
}
