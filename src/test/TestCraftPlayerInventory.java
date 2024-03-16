package test;

import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.inventory.items.CraftingTableCraft;
import fr.math.minecraft.shared.inventory.items.StickCraft;
import fr.math.minecraft.shared.world.Material;
import org.junit.Assert;
import org.junit.Test;

public class TestCraftPlayerInventory {

    @Test
    public void testStickCraft() {
        PlayerCraftInventory inventory = new PlayerCraftInventory();
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 0);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 2);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new StickCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.STICK);
    }

    @Test
    public void testCraftingTableCraft() {
        PlayerCraftInventory inventory = new PlayerCraftInventory();
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 0);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 1);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 2);
        inventory.setItem(new ItemStack(Material.OAK_PLANKS, 1), 3);

        CraftController craftController = new CraftController();
        craftController.registerCraft(new CraftingTableCraft());
        ItemStack itemStack = craftController.getCraft(inventory);

        Assert.assertEquals(itemStack.getMaterial(), Material.CRAFTING_TABLE);
    }

}
