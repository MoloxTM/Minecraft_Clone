package fr.math.minecraft.client.events;

public class PlayerPacketMoveEvent {

    private final String message;

    public PlayerPacketMoveEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
