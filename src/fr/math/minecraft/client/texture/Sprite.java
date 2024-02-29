package fr.math.minecraft.client.texture;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.entity.player.PlayerAction;

public class Sprite {

    private final static int ANIMATION_SPEED = 20;
    private int index;
    private int tick;

    public Sprite() {
        this.index = 0;
        this.tick = 0;
    }

    public void update(Player player) {
        tick++;
        PlayerAction action = player.getAction();
        if (tick >= ANIMATION_SPEED) {
            if (action != null && index < action.getLength()) {
                index++;
                if (index == action.getLength()) {
                    System.out.println("?");
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

}
