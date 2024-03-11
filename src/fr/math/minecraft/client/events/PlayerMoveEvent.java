package fr.math.minecraft.client.events;

import fr.math.minecraft.client.entity.player.Player;

public class PlayerMoveEvent {

    private final Player player;

    public PlayerMoveEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
