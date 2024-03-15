package fr.math.minecraft.client.events.listeners;

import fr.math.minecraft.client.events.*;

public interface PacketEventListener {

    void onServerState(ServerStateEvent event);
    void onPlayerListPacket(PlayerListPacketEvent event);
    void onSkinPacket(SkinPacketEvent event);
    void onChunkPacket(ChunkPacketEvent event);
    void onPlayerState(PlayerStateEvent event);
    void onDroppedItemState(DroppedItemEvent event);
    void onPlacedBlockState(PlacedBlockStateEvent event);
    void onBrokenBlockState(BrokenBlockStateEvent event);
    void onChatState(ChatPayloadStateEvent event);

}
