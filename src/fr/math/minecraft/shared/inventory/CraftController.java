package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.inventory.items.*;
import fr.math.minecraft.shared.inventory.items.armor.*;
import fr.math.minecraft.shared.inventory.items.axe.DiamondAxeCraft;
import fr.math.minecraft.shared.inventory.items.axe.IronAxeCraft;
import fr.math.minecraft.shared.inventory.items.axe.StoneAxeCraft;
import fr.math.minecraft.shared.inventory.items.axe.WoodenAxeCraft;
import fr.math.minecraft.shared.inventory.items.pickaxe.DiamondPickaxeCraft;
import fr.math.minecraft.shared.inventory.items.pickaxe.IronPickaxeCraft;
import fr.math.minecraft.shared.inventory.items.pickaxe.StonePickaxeCraft;
import fr.math.minecraft.shared.inventory.items.pickaxe.WoodenPickaxeCraft;
import fr.math.minecraft.shared.inventory.items.shovel.DiamondShovelCraft;
import fr.math.minecraft.shared.inventory.items.shovel.IronShovelCraft;
import fr.math.minecraft.shared.inventory.items.shovel.StoneShovelCraft;
import fr.math.minecraft.shared.inventory.items.shovel.WoodenShovelCraft;
import fr.math.minecraft.shared.inventory.items.sword.DiamondSwordCraft;
import fr.math.minecraft.shared.inventory.items.sword.IronSwordCraft;
import fr.math.minecraft.shared.inventory.items.sword.StoneSwordCraft;
import fr.math.minecraft.shared.inventory.items.sword.WoodenSwordCraft;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftController {

    private ArrayList<CraftRecipes> recipes;
    private static CraftController instance = null;

    private CraftController() {
        this.recipes = new ArrayList<>();

        registerCraft(new DiamondBootsCraft());
        registerCraft(new DiamondChessplateCraft());
        registerCraft(new DiamondHelmetCraft());
        registerCraft(new DiamondPantsCraft());
        registerCraft(new IronBootsCraft());
        registerCraft(new IronChessplateCraft());
        registerCraft(new IronChessplateCraft());
        registerCraft(new IronHelmetCraft());
        registerCraft(new IronPantsCraft());
        registerCraft(new LeatherBootsCraft());
        registerCraft(new LeatherChessplateCraft());
        registerCraft(new LeatherHelmetCraft());
        registerCraft(new LeatherPantsCraft());
        registerCraft(new DiamondAxeCraft());
        registerCraft(new IronAxeCraft());
        registerCraft(new WoodenAxeCraft());
        registerCraft(new DiamondPickaxeCraft());
        registerCraft(new IronPickaxeCraft());
        registerCraft(new StoneAxeCraft());
        registerCraft(new StonePickaxeCraft());
        registerCraft(new StoneSwordCraft());
        registerCraft(new StoneShovelCraft());
        registerCraft(new DiamondShovelCraft());
        registerCraft(new IronShovelCraft());
        registerCraft(new DiamondSwordCraft());
        registerCraft(new IronSwordCraft());
        registerCraft(new WoodenPickaxeCraft());
        registerCraft(new WoodenSwordCraft());
        registerCraft(new WoodenShovelCraft());
        registerCraft(new ChestCraft());
        registerCraft(new CraftingTableCraft());
        registerCraft(new FurnaceCraft());
        registerCraft(new OakPlanksCraft());
        registerCraft(new StickCraft());
    }

    public void clearInventory(CraftRecipes craft, Inventory inventory) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            for (CraftData craftData : craft.getPlayerInventory()) {
                for (byte block : craftData.getTabCraft()) {
                    ItemStack item = inventory.getItems()[slot];
                    if (item != null && item.getMaterial().getId() == block) {
                        item.setAmount(item.getAmount() - 1);
                        if (item.getAmount() == 0) {
                            inventory.getItems()[slot] = null;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<CraftRecipes> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<CraftRecipes> recipes) {
        this.recipes = recipes;
    }

    public void registerCraft(CraftRecipes craftRecipes) {
        recipes.add(craftRecipes);
    }

    public CraftRecipes getCraft(PlayerCraftInventory playerCraftInventory) {
        for (CraftRecipes craftRecipes : recipes) {
            for (int i = 0; i < craftRecipes.getPlayerInventory().size(); i++) {
                CraftData craftData = craftRecipes.getPlayerInventory().get(i);
                if (craftData.equals(playerCraftInventory)) {
                    return craftRecipes;
                }
            }
        }
        return null;
    }

    public ItemStack getCraft(PlayerCraftingTableInventory playerCraftingTableInventory) {
        return null;
    }

    public CraftRecipes getCraft(CraftingTableInventory craftingTableInventory) {
        for (CraftRecipes craftRecipes : recipes) {
            for (int i = 0; i < craftRecipes.getCraftingTable().size(); i++) {
                CraftData craftData = craftRecipes.getCraftingTable().get(i);
                if (craftData.equals(craftingTableInventory)) {
                    return craftRecipes;
                }
            }
        }
        return null;
    }

    public static CraftController getInstance() {
        if (instance == null) {
            instance = new CraftController();
        }
        return instance;
    }
}
