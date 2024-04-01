package fr.math.minecraft.shared.inventory.items.axe;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class StoneAxeCraft extends CraftRecipes {

    public StoneAxeCraft() {
        super(new ItemStack(Material.STONE_AXE, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data1 = new CraftData(new byte[]
                {
                        Material.COBBLESTONE.getId(), Material.COBBLESTONE.getId(), Material.AIR.getId(),
                        Material.COBBLESTONE.getId(), Material.STICK.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        CraftData data2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.COBBLESTONE.getId(), Material.COBBLESTONE.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.COBBLESTONE.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        craftingTable.add(data1);
        craftingTable.add(data2);

    }
}
