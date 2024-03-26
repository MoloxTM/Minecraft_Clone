package test;

import fr.math.minecraft.shared.inventory.CraftController;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.inventory.PlayerCraftingTableInventory;
import fr.math.minecraft.shared.inventory.items.ChestCraft;
import fr.math.minecraft.shared.inventory.items.FurnaceCraft;
import fr.math.minecraft.shared.inventory.items.armor.DiamondBootsCraft;
import fr.math.minecraft.shared.inventory.items.armor.DiamondPantsCraft;
import fr.math.minecraft.shared.inventory.items.armor.IronChessplateCraft;
import fr.math.minecraft.shared.inventory.items.armor.LeatherHelmetCraft;
import fr.math.minecraft.shared.inventory.items.axe.DiamondAxeCraft;
import fr.math.minecraft.shared.inventory.items.pickaxe.WoodenPickaxeCraft;
import fr.math.minecraft.shared.inventory.items.shovel.IronShovelCraft;
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

        CraftController craftController = CraftController.getInstance();
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

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new FurnaceCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.FURNACE);
    }

    @Test
    public void testDiamondSwordCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.STICK, 1), 6);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 4);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController2 = CraftController.getInstance();
        craftController2.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack2 = craftController2.getCraft(inventory2);

        PlayerCraftingTableInventory inventory3 = new PlayerCraftingTableInventory();
        inventory3.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory3.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory3.setItem(new ItemStack(Material.STICK, 1), 8);

        CraftController craftController3 = CraftController.getInstance();
        craftController3.registerCraft(new DiamondSwordCraft());
        ItemStack itemStack3 = craftController3.getCraft(inventory3);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_SWORD);
        Assert.assertEquals(itemStack2.getMaterial(), Material.DIAMOND_SWORD);
        Assert.assertEquals(itemStack3.getMaterial(), Material.DIAMOND_SWORD);

    }

    @Test
    public void testDiamondAxeCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new DiamondAxeCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController2 = CraftController.getInstance();
        craftController2.registerCraft(new DiamondAxeCraft());
        ItemStack itemStack2 = craftController2.getCraft(inventory2);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_AXE);
        Assert.assertEquals(itemStack2.getMaterial(), Material.DIAMOND_AXE);
    }

    @Test
    public void testIronShovelCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 0);
        inventory.setItem(new ItemStack(Material.STICK, 1), 3);
        inventory.setItem(new ItemStack(Material.STICK, 1), 6);


        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new IronShovelCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.IRON_INGOT, 1), 1);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory2.setItem(new ItemStack(Material.STICK, 1), 7);


        CraftController craftController2 = CraftController.getInstance();
        craftController2.registerCraft(new IronShovelCraft());
        ItemStack itemStack2 = craftController2.getCraft(inventory2);

        PlayerCraftingTableInventory inventory3 = new PlayerCraftingTableInventory();
        inventory3.setItem(new ItemStack(Material.IRON_INGOT, 1), 2);
        inventory3.setItem(new ItemStack(Material.STICK, 1), 5);
        inventory3.setItem(new ItemStack(Material.STICK, 1), 8);


        CraftController craftController3 = CraftController.getInstance();
        craftController3.registerCraft(new IronShovelCraft());
        ItemStack itemStack3 = craftController.getCraft(inventory3);


        Assert.assertEquals(itemStack.getMaterial(), Material.IRON_SHOVEL);
        Assert.assertEquals(itemStack2.getMaterial(), Material.IRON_SHOVEL);
        Assert.assertEquals(itemStack3.getMaterial(), Material.IRON_SHOVEL);
    }

    @Test
    public void testWoodenPickaxeCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 0);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 1);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 2);
        inventory.setItem(new ItemStack(Material.STICK, 1), 4);
        inventory.setItem(new ItemStack(Material.STICK, 1), 7);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new WoodenPickaxeCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.WOODEN_PICKAXE);
    }

    @Test
    public void testLeatherHelmetCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.LEATHER, 1), 0);
        inventory.setItem(new ItemStack(Material.LEATHER, 1), 1);
        inventory.setItem(new ItemStack(Material.LEATHER, 1), 2);
        inventory.setItem(new ItemStack(Material.LEATHER, 1), 3);
        inventory.setItem(new ItemStack(Material.LEATHER, 1), 5);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new LeatherHelmetCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.LEATHER, 1), 3);
        inventory2.setItem(new ItemStack(Material.LEATHER, 1), 4);
        inventory2.setItem(new ItemStack(Material.LEATHER, 1), 5);
        inventory2.setItem(new ItemStack(Material.LEATHER, 1), 6);
        inventory2.setItem(new ItemStack(Material.LEATHER, 1), 8);

        CraftController craftController2 = CraftController.getInstance();
        craftController2.registerCraft(new LeatherHelmetCraft());
        ItemStack itemStack2 = craftController.getCraft(inventory2);

        Assert.assertEquals(itemStack.getMaterial(), Material.LEATHER_HELMET);
        Assert.assertEquals(itemStack2.getMaterial(), Material.LEATHER_HELMET);
    }

    @Test
    public void testIronChessplateCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 0);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 2);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 3);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 4);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 5);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 6);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 7);
        inventory.setItem(new ItemStack(Material.IRON_INGOT, 1), 8);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new IronChessplateCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.IRON_CHESSPLATE);
    }

    @Test
    public void testDiamondPantsCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 1);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 6);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 8);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new DiamondPantsCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_PANTS);
    }

    @Test
    public void testDiamondBootsCraft() {
        PlayerCraftingTableInventory inventory = new PlayerCraftingTableInventory();
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 0);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 2);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory.setItem(new ItemStack(Material.DIAMOND, 1), 5);

        CraftController craftController = CraftController.getInstance();
        craftController.registerCraft(new DiamondBootsCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        PlayerCraftingTableInventory inventory2 = new PlayerCraftingTableInventory();
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 3);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 5);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 6);
        inventory2.setItem(new ItemStack(Material.DIAMOND, 1), 8);

        CraftController craftController2 = CraftController.getInstance();
        craftController2.registerCraft(new DiamondBootsCraft());
        ItemStack itemStack2 = craftController.getCraft(inventory2);

        Assert.assertEquals(itemStack.getMaterial(), Material.DIAMOND_BOOTS);
        Assert.assertEquals(itemStack2.getMaterial(), Material.DIAMOND_BOOTS);
    }

}
