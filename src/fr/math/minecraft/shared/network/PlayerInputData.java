package fr.math.minecraft.shared.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joml.Vector3i;

import java.util.Queue;

public class PlayerInputData {

    private final Vector3i inputVector;
    private final float yaw;
    private final float pitch;
    public PlayerInputData(Vector3i inputVector, float yaw, float pitch) {
        this.inputVector = inputVector;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public ObjectNode toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("inputX", inputVector.x);
        node.put("inputY", inputVector.y);
        node.put("inputZ", inputVector.z);
        node.put("yaw", yaw);
        node.put("pitch", pitch);

        return node;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public Vector3i getInputVector() {
        return inputVector;
    }
}
