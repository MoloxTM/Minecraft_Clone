package fr.math.minecraft.client.math;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Chunk;
import org.joml.Vector3f;

public class FrustrumCulling {

    private final Camera camera;
    private final float yFactor;
    private final float tanY;
    private final float xFactor;
    private final float tanX;

    public FrustrumCulling(Camera camera) {
        this.camera = camera;
        float halfVerticalFovRadians = (float) Math.toRadians(camera.getFov() * 0.5f);
        float cameraAspect = GameConfiguration.WINDOW_WIDTH / GameConfiguration.WINDOW_HEIGHT;
        float horizontalFov = (float) (2 * Math.atan(Math.tan(halfVerticalFovRadians) * cameraAspect));
        this.yFactor = (float) (1.0f / Math.cos(halfVerticalFovRadians));
        this.tanY = (float) Math.tan(halfVerticalFovRadians);
        this.xFactor = (float) (1.0f / Math.cos(horizontalFov * 0.5f));
        this.tanX = (float) Math.tan(horizontalFov * 0.5f);
    }

    public boolean isVisible(Chunk chunk) {
        return this.isVisible(chunk.getCenter());
    }

    public boolean isVisible(Vector3f position) {

        Vector3f chunkSphere = new Vector3f(position).sub(camera.getPosition());

        float zDistance = chunkSphere.dot(camera.getFront());

        if (zDistance < camera.getNearPlane() - Chunk.SPHERE_RADIUS || zDistance > camera.getFarPlane() + Chunk.SPHERE_RADIUS) {
            return false;
        }

        float yDistance = chunkSphere.dot(camera.getUp());
        float distance = this.yFactor * Chunk.SPHERE_RADIUS + zDistance * tanY;

        if (!(-distance <= yDistance && yDistance <= distance)) {
            return false;
        }

        float xDistance = chunkSphere.dot(camera.getRight());
        distance = this.xFactor * Chunk.SPHERE_RADIUS + zDistance * this.tanX;

        if (!(-distance <= xDistance && xDistance <= distance)) {
            return false;
        }

        return true;
    }

}
