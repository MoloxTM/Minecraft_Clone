package fr.math.minecraft.client.events;

import fr.math.minecraft.client.manager.WorldManager;

public class PlayerListener implements EventListener {

    private final WorldManager worldManager;

    public PlayerListener() {
        this.worldManager = new WorldManager();
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        // worldManager.loadChunks();
    }

}
