package test;

import fr.math.minecraft.shared.inventory.CraftController;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.inventory.PlayerCraftingTableInventory;
import fr.math.minecraft.shared.inventory.items.ChestCraft;
import fr.math.minecraft.shared.inventory.items.FurnaceCraft;
import fr.math.minecraft.shared.inventory.items.StickCraft;
import fr.math.minecraft.shared.inventory.items.axe.DiamondAxeCraft;
import fr.math.minecraft.shared.inventory.items.sword.DiamondSwordCraft;
import fr.math.minecraft.shared.world.Material;
import org.junit.Assert;
import org.junit.Test;

public class TestCraftCraftingTable {

    @Test
    public void testChestCraft() {

        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 0);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 1);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 2);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 3);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 5);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 6);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 7);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 8);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new ChestCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.CHEST);
    }

    @Test
    public void testFurnaceCraft() {

        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 0);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 1);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 2);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 3);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 5);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 6);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 7);
        inventory.setItem(new ItemStack(Material.COBBLESTONE, 1), 8);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new FurnaceCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.FURNACE);
    }

    @Test
    public void testDiamondSword() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.STICK, 1), 6);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 4);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController2 = new CraftController();
        craftController2.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack2 = craftController2.getCraft(inventory2);

        PlayerCraftingTableInventory inventory3 = new PlayerCraftingTableInventory();
        inventory3.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory3.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory3.setItem(new ItemStack(Material.STICK, 1), 8);

        CraftController craftController3 = new CraftController();
        craftController3.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack3 = craftController3.getCraft(inventory3);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_SWORD);
        Assert.assertEquals(itemStack2.getMaterial(), Material.DIAMOND_SWORD);
        Assert.assertEquals(itemStack3.getMaterial(), Material.DIAMOND_SWORD);

    }

    @Test
    public void testDiamondAxe() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new DiamondAxeCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController2 = new CraftController();
        craftController2.registerCraft(new DiamondAxeCraft());
        ItemStack itemStack2 = craftController2.getCraft(inventory2);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_AXE);
        Assert.assertEquals(itemStack2.getMaterial(), Material.DIAMOND_AXE);
    }

}
