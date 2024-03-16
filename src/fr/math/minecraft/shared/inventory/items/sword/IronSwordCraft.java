package fr.math.minecraft.shared.inventory.items.sword;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class IronSwordCraft extends CraftRecipes {

    public IronSwordCraft() {
        super(new ItemStack(Material.IRON_SWORD, 1));
        fillRecipe();
    }
    @Override
    public void fillRecipe() {
        CraftData data1 = new CraftData(new byte[]
                {
                        Material.IRON_INGOT.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.IRON_INGOT.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.STICK.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData data2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.IRON_INGOT.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.IRON_INGOT.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.STICK.getId(), Material.AIR.getId()
                }
        );

        CraftData data3 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.IRON_INGOT.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.IRON_INGOT.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.STICK.getId()
                }
        );

        craftingTable.add(data1);
        craftingTable.add(data2);
        craftingTable.add(data3);

    }
}
