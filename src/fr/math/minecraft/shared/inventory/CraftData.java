package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.shared.world.Material;

public class CraftData {

    private int[] tabCraft;

    public CraftData(int[] tabCraft) {
        this.tabCraft = tabCraft;
    }

    public int[] getTabCraft() {
        return tabCraft;
    }

    public void setTabCraft(int[] tabCraft) {
        this.tabCraft = tabCraft;
    }

}
