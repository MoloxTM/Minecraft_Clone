package fr.math.minecraft.client.entity;

import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.world.World;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class AttackRay extends Ray {

    private Entity target;

    public AttackRay(float reach) {
        super(reach);
        this.target = null;
    }

    public void update(Vector3f position, Vector3f front, World world, boolean isServer) {

        target = null;
        Vector3f startPoint = new Vector3f(position).add(0.0f, 0.5f, 0.0f);
        Vector3i rayPosition = new Vector3i((int)startPoint.x, (int)startPoint.y, (int)startPoint.z);
        Vector3f endPoint = new Vector3f(startPoint);

        endPoint.add(new Vector3f(front).mul(reach));

        float dx, dy, dz, tDeltaX, tDeltaY, tDeltaZ, tMaxX, tMaxY, tMaxZ;

        dx = Math.signum(endPoint.x - startPoint.x);
        tDeltaX = dx != 0 ? Math.min(dx / (endPoint.x - startPoint.x), 10000000.0f) :  10000000.0f;
        tMaxX = dx > 0 ? tDeltaX * MathUtils.fra1(startPoint.x) : tDeltaX * MathUtils.fra0(startPoint.x);

        dy = Math.signum(endPoint.y - startPoint.y);
        tDeltaY = dy != 0 ? Math.min(dy / (endPoint.y - startPoint.y), 10000000.0f) :  10000000.0f;
        tMaxY = dy > 0 ? tDeltaY * MathUtils.fra1(startPoint.y) : tDeltaY * MathUtils.fra0(startPoint.y);

        dz = Math.signum(endPoint.z - startPoint.z);
        tDeltaZ = dz != 0 ? Math.min(dz / (endPoint.z - startPoint.z), 10000000.0f) :  10000000.0f;
        tMaxZ = dz > 0 ? tDeltaZ * MathUtils.fra1(startPoint.z) : tDeltaZ * MathUtils.fra0(startPoint.z);

        while (!(tMaxX > 1 && tMaxY > 1 && tMaxZ > 1)){
            if (tMaxX < tMaxY && tMaxX < tMaxZ) {
                rayPosition.x += dx;
                tMaxX += tDeltaX;
            } else if (tMaxY < tMaxX && tMaxY < tMaxZ) {
                rayPosition.y += dy;
                tMaxY += tDeltaY;
            } else {
                rayPosition.z += dz;
                tMaxZ += tDeltaZ;
            }

            synchronized (world.getEntities()) {
                for (Entity entity : world.getEntities().values()) {
                    if (entity.getType() == EntityType.PLAYER) {
                        continue;
                    }
                    if (entity.getPosition().distance(new Vector3f(rayPosition)) < 1.0f) {
                        target = entity;
                    }
                }
            }
        }
    }

    public Entity getTarget() {
        return target;
    }

}
