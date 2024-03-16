package fr.math.minecraft.shared.inventory.items;

import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class StickCraft extends CraftRecipes {

    public StickCraft() {
        super(new ItemStack(Material.STICK, 2));
        fillRecipe();
    }

    @Override
    public void fillRecipe() {

        CraftData dataPlayerInventory_1 = new CraftData(new byte[]
            {
                Material.OAK_PLANKS.getId(), Material.AIR.getId(),
                Material.OAK_PLANKS.getId(), Material.AIR.getId()
            }
        );

        CraftData dataPlayerInventory_2 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.OAK_PLANKS.getId(),
                Material.AIR.getId(), Material.OAK_PLANKS.getId()
            }
        );

        playerInventory.add(dataPlayerInventory_1);
        playerInventory.add(dataPlayerInventory_2);

        CraftData dataCraftingTable_1 = new CraftData(new byte[]
            {
                Material.OAK_PLANKS.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.OAK_PLANKS.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
            }
        );

        CraftData dataCraftingTable_2 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.OAK_PLANKS.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.OAK_PLANKS.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
            }
        );

        CraftData dataCraftingTable_3 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.AIR.getId(), Material.OAK_PLANKS.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.OAK_PLANKS.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
            }
        );

        CraftData dataCraftingTable_4 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.OAK_PLANKS.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.OAK_PLANKS.getId(), Material.AIR.getId(), Material.AIR.getId()
            }
        );

        CraftData dataCraftingTable_5 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.OAK_PLANKS.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.OAK_PLANKS.getId(), Material.AIR.getId()
            }
        );

        CraftData dataCraftingTable_6 = new CraftData(new byte[]
            {
                Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.OAK_PLANKS.getId(),
                Material.AIR.getId(), Material.AIR.getId(), Material.OAK_PLANKS.getId()
            }
        );

        craftingTable.add(dataCraftingTable_1);
        craftingTable.add(dataCraftingTable_2);
        craftingTable.add(dataCraftingTable_3);
        craftingTable.add(dataCraftingTable_4);
        craftingTable.add(dataCraftingTable_5);
        craftingTable.add(dataCraftingTable_6);

    }
}
