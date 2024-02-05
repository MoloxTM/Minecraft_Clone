package fr.math.minecraft.client.events;

public class SkinPacketEvent {

    private final String skin;
    private final String playerUuid;

    public SkinPacketEvent(String skin, String playerUuid) {
        this.skin = skin;
        this.playerUuid = playerUuid;
    }

    public String getSkin() {
        return skin;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }
}
