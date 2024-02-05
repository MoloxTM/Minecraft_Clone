package fr.math.minecraft.client.events;

import fr.math.minecraft.client.network.payload.StatePayload;

public class ServerStateEvent {

    private final StatePayload statePayload;

    public ServerStateEvent(StatePayload statePayload) {
        this.statePayload = statePayload;
    }

    public StatePayload getStatePayload() {
        return statePayload;
    }
}
