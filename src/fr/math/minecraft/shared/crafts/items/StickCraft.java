package fr.math.minecraft.shared.crafts.items;

import fr.math.minecraft.shared.crafts.CraftData;
import fr.math.minecraft.shared.crafts.CraftRecipes;
import fr.math.minecraft.shared.world.Material;

public class StickCraft extends CraftRecipes {

    public StickCraft() {
        super();
        fillRecipe();
    }

    @Override
    public void fillRecipe() {
        CraftData dataPlayerInventory_0 = new CraftData(0, Material.OAK_PLANKS);
        CraftData dataPlayerInventory_2 = new CraftData(2, Material.OAK_PLANKS);

        CraftData dataPlayerInventory_1 = new CraftData(1, Material.OAK_PLANKS);
        CraftData dataPlayerInventory_3 = new CraftData(3, Material.OAK_PLANKS);

        playerInventory.add(dataPlayerInventory_0);
        playerInventory.add(dataPlayerInventory_2);
        playerInventory.add(dataPlayerInventory_1);
        playerInventory.add(dataPlayerInventory_3);

        CraftData dataCrafttingTable_0 = new CraftData(0, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_1 = new CraftData(1, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_2 = new CraftData(2, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_3 = new CraftData(3, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_4 = new CraftData(4, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_5 = new CraftData(5, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_6 = new CraftData(6, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_7 = new CraftData(7, Material.OAK_PLANKS);
        CraftData dataCrafttingTable_8 = new CraftData(8, Material.OAK_PLANKS);

        craftingTable.add(dataCrafttingTable_0);
        craftingTable.add(dataCrafttingTable_1);
        craftingTable.add(dataCrafttingTable_2);
        craftingTable.add(dataCrafttingTable_3);
        craftingTable.add(dataCrafttingTable_4);
        craftingTable.add(dataCrafttingTable_5);
        craftingTable.add(dataCrafttingTable_6);
        craftingTable.add(dataCrafttingTable_7);
        craftingTable.add(dataCrafttingTable_8);

    }
}
