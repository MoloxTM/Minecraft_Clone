package fr.math.minecraft.inventory;

import fr.math.minecraft.client.animations.HotbarAnimation;

public class Hotbar extends Inventory {

    private final HotbarAnimation animation;

    public Hotbar() {
        super();
        this.items = new ItemStack[9];
        this.animation = new HotbarAnimation();
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
    public ItemStack getSelectedItem() {

        if (currentSlot < items.length) {
            return items[currentSlot];
        }

        return null;
    }

    public HotbarAnimation getAnimation() {
        return animation;
    }
}
