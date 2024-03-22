package fr.math.minecraft.shared.inventory.items;

import fr.math.minecraft.shared.inventory.CraftData;
import fr.math.minecraft.shared.inventory.CraftRecipes;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.Material;

public class OakPlanksCraft extends CraftRecipes {


    public OakPlanksCraft() {
        super(new ItemStack(Material.OAK_PLANKS, 4));
        fillRecipe();
    }

    @Override
    public void fillRecipe() {
        CraftData dataPlayerInventory_1 = new CraftData(new byte[]
                {
                        Material.OAK_LOG.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.OAK_LOG.getId(),
                        Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_3 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(),
                        Material.OAK_LOG.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_4 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.OAK_LOG.getId()
                }
        );

        CraftData dataPlayerInventory_5 = new CraftData(new byte[]
                {
                        Material.SPRUCE_WOOD.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_6 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.SPRUCE_WOOD.getId(),
                        Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_7 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(),
                        Material.SPRUCE_WOOD.getId(), Material.AIR.getId()
                }
        );

        CraftData dataPlayerInventory_8 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.SPRUCE_WOOD.getId()
                }
        );


        playerInventory.add(dataPlayerInventory_1);
        playerInventory.add(dataPlayerInventory_2);
        playerInventory.add(dataPlayerInventory_3);
        playerInventory.add(dataPlayerInventory_4);
        playerInventory.add(dataPlayerInventory_5);
        playerInventory.add(dataPlayerInventory_6);
        playerInventory.add(dataPlayerInventory_7);
        playerInventory.add(dataPlayerInventory_8);

        CraftData dataCraftingTable_1 = new CraftData(new byte[]
                {
                        Material.OAK_LOG.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_2 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.OAK_LOG.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_3 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.OAK_LOG.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_4 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.OAK_LOG.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_5 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.OAK_LOG.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_6 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.OAK_LOG.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_7 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.OAK_LOG.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_8 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.OAK_LOG.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_9 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.OAK_LOG.getId()
                }
        );

        CraftData dataCraftingTable_10 = new CraftData(new byte[]
                {
                        Material.SPRUCE_WOOD.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_11 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.SPRUCE_WOOD.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_12 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.SPRUCE_WOOD.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_13 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.SPRUCE_WOOD.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_14 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.SPRUCE_WOOD.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_15 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.SPRUCE_WOOD.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_16 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.SPRUCE_WOOD.getId(), Material.AIR.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_17 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.SPRUCE_WOOD.getId(), Material.AIR.getId()
                }
        );

        CraftData dataCraftingTable_18 = new CraftData(new byte[]
                {
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.AIR.getId(),
                        Material.AIR.getId(), Material.AIR.getId(), Material.SPRUCE_WOOD.getId()
                }
        );

        craftingTable.add(dataCraftingTable_1);
        craftingTable.add(dataCraftingTable_2);
        craftingTable.add(dataCraftingTable_3);
        craftingTable.add(dataCraftingTable_4);
        craftingTable.add(dataCraftingTable_5);
        craftingTable.add(dataCraftingTable_6);
        craftingTable.add(dataCraftingTable_7);
        craftingTable.add(dataCraftingTable_8);
        craftingTable.add(dataCraftingTable_9);
        craftingTable.add(dataCraftingTable_10);
        craftingTable.add(dataCraftingTable_11);
        craftingTable.add(dataCraftingTable_12);
        craftingTable.add(dataCraftingTable_13);
        craftingTable.add(dataCraftingTable_14);
        craftingTable.add(dataCraftingTable_15);
        craftingTable.add(dataCraftingTable_16);
        craftingTable.add(dataCraftingTable_17);
        craftingTable.add(dataCraftingTable_18);
    }
}
