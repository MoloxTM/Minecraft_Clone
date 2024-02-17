package fr.math.minecraft.client.network.payload;

import fr.math.minecraft.shared.network.PlayerInputData;

import java.util.ArrayList;
import java.util.List;

public class InputPayload {

    private final int tick;
    private final List<PlayerInputData> inputData;

    public InputPayload(int tick, List<PlayerInputData> inputData) {
        this.tick = tick;
        this.inputData = inputData;
    }

    public InputPayload(int tick) {
        this.tick = tick;
        this.inputData = new ArrayList<>();
    }

    public int getTick() {
        return tick;
    }

    public List<PlayerInputData> getInputData() {
        return inputData;
    }
}
