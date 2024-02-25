package fr.math.minecraft.client.entity;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.shared.MathUtils;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Ray {

    private Chunk aimedChunk;
    private byte aimedBlock;
    private Vector3i blockWorldPosition;

    private float reach;

    public Ray(float reach) {
        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();
        this.blockWorldPosition = new Vector3i();
        this.reach = reach;
    }


    public void update(Camera camera, World world) {

        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();

        Vector3f startPoint = new Vector3f(camera.getPosition()).add(0.0f, 0.5f, 0.0f);
        Vector3i rayPositon = new Vector3i((int)startPoint.x, (int)startPoint.y, (int)startPoint.z);
        Vector3f endPoint = new Vector3f(startPoint);

        endPoint.add(new Vector3f(camera.getFront()).mul(reach));

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
            if(tMaxX < tMaxY && tMaxX < tMaxZ) {
                rayPositon.x += dx;
                tMaxX += tDeltaX;
            } else if(tMaxY < tMaxX && tMaxY < tMaxZ) {
                rayPositon.y += dy;
                tMaxY += tDeltaY;
            } else {
                rayPositon.z += dz;
                tMaxZ += tDeltaZ;
            }
            this.aimedChunk = (world.getChunkAt(rayPositon.x, rayPositon.y, rayPositon.z));

            if(this.aimedChunk != null) {
                int blockX = rayPositon.x % Chunk.SIZE;
                int blockY = rayPositon.y % Chunk.SIZE;
                int blockZ = rayPositon.z % Chunk.SIZE;

                blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
                blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
                blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

                byte block = this.aimedChunk.getBlock(blockX, blockY, blockZ);
                if (block != Material.AIR.getId() && block != Material.WATER.getId()) {
                    this.blockWorldPosition.x = rayPositon.x;
                    this.blockWorldPosition.y = rayPositon.y;
                    this.blockWorldPosition.z = rayPositon.z;
                    this.aimedBlock = block;
                    break;
                }
            }
        }
    }

    public Vector3i getBlockWorldPosition() {
        return this.blockWorldPosition;
    }

    public byte getAimedBlock() {
        return aimedBlock;
    }

    public Chunk getAimedChunk() {
        return aimedChunk;
    }

    public float getReach() {
        return reach;
    }

    public void setAimedBlock(byte aimedBlock) {
        this.aimedBlock = aimedBlock;
    }

    public void setAimedChunk(Chunk aimedChunk) {
        this.aimedChunk = aimedChunk;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }
}
