package fr.math.minecraft.shared.inventory;

import fr.math.minecraft.client.animations.HotbarAnimation;
import fr.math.minecraft.shared.GameConfiguration;

public class Hotbar extends Inventory {

    private final HotbarAnimation animation;
    private int selectedSlot;

    public Hotbar() {
        super();
        this.type = InventoryType.HOTBAR;
        this.items = new ItemStack[9];
        this.animation = new HotbarAnimation();
        this.selectedSlot = 0;
    }

    public ItemStack[] getItems() {
        return items;
    }

    @Override
    public void setCurrentSlot(int currentSlot) {
        super.setCurrentSlot(currentSlot);
        animation.start();
    }

    @Override
    public float getItemX(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleX = inventoryWidth / 177.0f;
        float slotWidth = 18.0f * slotScaleX;
        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;

        return 8.0f * slotScaleX + inventoryX + slot * slotWidth;
    }

    @Override
    public float getItemY(int slot) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();
        float slotScaleY = inventoryHeight / 166.0f;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;

        return inventoryY + 8.0f * slotScaleY;
    }

    public HotbarAnimation getAnimation() {
        return animation;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }
}
