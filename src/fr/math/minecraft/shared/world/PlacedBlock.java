package fr.math.minecraft.shared.world;

import org.joml.Vector3i;

import java.util.Objects;

public class PlacedBlock {

    private final Vector3i worldPosition;
    private final Vector3i localPosition;
    private final byte block;

    public PlacedBlock(Vector3i worldPosition, Vector3i localPosition, byte block) {
        this.worldPosition = worldPosition;
        this.localPosition = localPosition;
        this.block = block;
    }

    public Vector3i getWorldPosition() {
        return worldPosition;
    }

    public Vector3i getLocalPosition() {
        return localPosition;
    }

    public byte getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacedBlock that = (PlacedBlock) o;
        return block == that.block && Objects.equals(worldPosition, that.worldPosition) && Objects.equals(localPosition, that.localPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldPosition, localPosition, block);
    }

    @Override
    public String toString() {
        return "PlacedBlock{" +
                "worldPosition=" + worldPosition +
                ", localPosition=" + localPosition +
                ", block=" + block +
                '}';
    }
}
