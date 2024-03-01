package fr.math.minecraft.client.meshs;

import org.joml.Vector3i;

public enum Face {

    UP(new Vector3i(0, 1, 0)),
    DOWN(new Vector3i(0, -1, 0)),
    NORTH(new Vector3i(0, 0, 1)),
    SOUTH(new Vector3i(0, 0, -1)),
    EST(new Vector3i(1, 0, 0)),
    WEST(new Vector3i(-1, 0, 0));
    private Vector3i normal;

    Face(Vector3i normal) {
        this.normal = normal;
    }

    public Vector3i getNormal() {
        return normal;
    }
}
