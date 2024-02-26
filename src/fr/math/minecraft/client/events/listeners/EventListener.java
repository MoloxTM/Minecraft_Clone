package fr.math.minecraft.client.events.listeners;

import fr.math.minecraft.client.events.BlockBreakEvent;
import fr.math.minecraft.client.events.PlayerJoinEvent;
import fr.math.minecraft.client.events.PlayerMoveEvent;

public interface EventListener {

    void onPlayerMove(PlayerMoveEvent event);
    void onPlayerJoin(PlayerJoinEvent event);
    void onBlockBreak(BlockBreakEvent event);

}
