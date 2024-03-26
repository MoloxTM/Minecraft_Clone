package fr.math.minecraft.client.entity;

import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.world.World;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class AttackRay extends Ray {

    private Entity target;
    private Client clientTarget;

    public AttackRay(float reach) {
        super(reach);
        this.target = null;
    }

    public void update(Vector3f position, String playerUuid, Vector3f front, World world, boolean isServer) {

        target = null;
        clientTarget = null;
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

            float targetDistance = -1;

            synchronized (world.getEntities()) {
                for (Entity entity : world.getEntities().values()) {
                    float entityDistance = entity.getPosition().distance(new Vector3f(rayPosition));
                    if (entityDistance < 1.0f) {
                        target = entity;
                        targetDistance = entityDistance;
                    }
                }
            }

            if (isServer) {
                MinecraftServer server = MinecraftServer.getInstance();
                synchronized (server.getClients()) {
                    for (Client client : server.getClients().values()) {
                        if (client.getUuid().equals(playerUuid)) {
                            continue;
                        }
                        float clientDistance = client.getPosition().distance(new Vector3f(rayPosition));
                        if (clientDistance < 1.0f) {
                            if (targetDistance == -1) {
                                clientTarget = client;
                                target = null;
                            } else {
                                if (clientDistance < targetDistance) {
                                    clientTarget = client;
                                    target = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Entity getTarget() {
        return target;
    }

    public Client getClientTarget() {
        return clientTarget;
    }
}
