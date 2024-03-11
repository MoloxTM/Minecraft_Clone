package fr.math.minecraft.client.events;

import fr.math.minecraft.client.entity.player.Player;
import org.joml.Vector3i;

import java.util.Vector;

public class BlockBreakEvent {
    private final Player player;
    private final Vector3i position;

    public BlockBreakEvent(Player player, Vector3i position) {
        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector3i getPosition() {
        return position;
    }
}
