package fr.math.minecraft.client.events;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.network.payload.StatePayload;

public class PlayerStateEvent {

    private final Player player;
    private final StatePayload statePayload;

    public PlayerStateEvent(Player player, StatePayload statePayload) {
        this.player = player;
        this.statePayload = statePayload;
    }

    public Player getPlayer() {
        return player;
    }

    public StatePayload getStatePayload() {
        return statePayload;
    }
}
