package fr.math.minecraft.client.events.listeners;

import fr.math.minecraft.client.events.ChunkPacketEvent;
import fr.math.minecraft.client.events.PlayerListPacketEvent;
import fr.math.minecraft.client.events.ServerStateEvent;
import fr.math.minecraft.client.events.SkinPacketEvent;

public interface PacketEventListener {

    void onServerState(ServerStateEvent event);
    void onPlayerListPacket(PlayerListPacketEvent event);
    void onSkinPacket(SkinPacketEvent event);
    void onChunkPacket(ChunkPacketEvent event);

}
