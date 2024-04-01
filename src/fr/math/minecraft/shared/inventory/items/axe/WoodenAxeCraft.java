package fr.math.minecraft.shared.inventory.items.axe;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class WoodenAxeCraft extends CraftRecipes {

    public WoodenAxeCraft() {
        super(new ItemStack(Material.WOODEN_AXE, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data1 = new CraftData(new byte[]
                {
                        Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(), Material.AIR.getId(),
                        Material.OAK_PLANKS.getId(), Material.STICK.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        CraftData data2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.OAK_PLANKS.getId(), Material.OAK_PLANKS.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.OAK_PLANKS.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        craftingTable.add(data1);
        craftingTable.add(data2);

    }
}
