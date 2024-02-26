package fr.math.minecraft.shared.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joml.Vector3i;

import java.util.Queue;

public class PlayerInputData {

    private final float yaw;
    private final float pitch;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean flying, sneaking, jumping, sprinting;
    private final boolean placingBlock, breakingBlock;


    public PlayerInputData(boolean movingLeft, boolean movingRight, boolean movingForward, boolean movingBackward, boolean flying, boolean sneaking, boolean jumping, float yaw, float pitch, boolean sprinting, boolean placingBlock, boolean breakingBlock) {
        this.movingLeft = movingLeft;
        this.movingRight = movingRight;
        this.movingForward = movingForward;
        this.movingBackward = movingBackward;
        this.flying = flying;
        this.sneaking = sneaking;
        this.jumping = jumping;
        this.yaw = yaw;
        this.pitch = pitch;
        this.sprinting = sprinting;
        this.breakingBlock = breakingBlock;
        this.placingBlock = placingBlock;
    }

    public ObjectNode toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("movingLeft", movingLeft);
        node.put("movingRight", movingRight);
        node.put("movingForward", movingForward);
        node.put("movingBackward", movingBackward);
        node.put("flying", flying);
        node.put("sneaking", sneaking);
        node.put("yaw", yaw);
        node.put("pitch", pitch);
        node.put("jumping", jumping);
        node.put("sprinting", sprinting);
        node.put("placingBlock", placingBlock);
        node.put("breakingBlock", breakingBlock);

        return node;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isBreakingBlock() {
        return breakingBlock;
    }

    public boolean isPlacingBlock() {
        return placingBlock;
    }
}
