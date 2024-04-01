package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.shared.world.Material;
import org.joml.Vector3f;

public enum ItemModelData {

    DIAMOND_SWORD(Material.DIAMOND_SWORD, new Vector3f(1.95f, -2.65f, -3.0f), new Vector3f(155.0f, 95.0f, 0.0f), 1.1f),
    APPLE(Material.APPLE, new Vector3f(2.2f, -3.5f, -4.0f), new Vector3f(165.0f, 95.0f, 0.0f), 1.6f),
    DIAMOND_AXE(Material.DIAMOND_AXE, new Vector3f(1.95f, -2.65f, -3.0f), new Vector3f(155.0f, 95.0f, 0.0f), 1.1f),
    DEBUG(Material.DEBUG, new Vector3f(1.95f, -2.65f, -3.0f), new Vector3f(155.0f, 95.0f, 0.0f), 1.1f);

    private final Material material;
    private final Vector3f translation;
    private final Vector3f rotation;
    private final float scale;

    ItemModelData(Material material, Vector3f translation, Vector3f rotation, float scale) {
        this.rotation = rotation;
        this.translation = translation;
        this.material = material;
        this.scale = scale;
    }

    public Material getMaterial() {
        return material;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
}
