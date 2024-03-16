package fr.math.minecraft.shared;

import fr.math.minecraft.client.entity.player.Player;

public class Sprite {

    private final static int ANIMATION_SPEED = 20;
    private int index;
    private int tick;

    public Sprite() {
        this.index = 0;
        this.tick = 0;
    }

    public void update(PlayerAction action) {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            if (action != null && index < action.getLength()) {
                index++;
                if (index == action.getLength()) {
                    index = 0;
                }
            }
            tick = 0;
        }
    }

    public void reset() {
        this.index = 0;
        this.tick = 0;
    }

    public int getIndex() {
        return index;
    }

    public int getTick() {
        return tick;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
