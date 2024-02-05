package fr.math.minecraft.client.events.listeners;

import fr.math.minecraft.client.events.ChunkPacketEvent;
import fr.math.minecraft.client.events.PlayerListPacketEvent;
import fr.math.minecraft.client.events.PlayerPacketMoveEvent;
import fr.math.minecraft.client.events.SkinPacketEvent;

public interface PacketEventListener {

    void onPlayerMovePacket(PlayerPacketMoveEvent event);
    void onPlayerListPacket(PlayerListPacketEvent event);
    void onSkinPacket(SkinPacketEvent event);
    void onChunkPacket(ChunkPacketEvent event);

}
