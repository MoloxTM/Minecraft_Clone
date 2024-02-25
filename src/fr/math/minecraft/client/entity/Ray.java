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

    private float reach;

    public Ray(float reach) {
        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();
        this.reach = reach;
    }


    public void update(Camera camera, World world) {

        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();

        Vector3f stratPoint = new Vector3f(camera.getPosition());
        Vector3i rayPositon = new Vector3i((int)stratPoint.x, (int)stratPoint.y, (int)stratPoint.z);
        Vector3f endPoint = new Vector3f(stratPoint);

        endPoint.add(new Vector3f(camera.getFront()).mul(reach));

        float dx, dy, dz, tDeltaX, tDeltaY, tDeltaZ, tMaxX, tMaxY, tMaxZ;

        dx = Math.signum(endPoint.x - stratPoint.x);
        tDeltaX = dx != 0 ? Math.min(dx/(endPoint.x - stratPoint.x), 10000000.0f) :  10000000.0f;
        tMaxX = dx > 0 ? tDeltaX * MathUtils.fra1(stratPoint.x) : tDeltaX * MathUtils.fra0(stratPoint.x);

        dy = Math.signum(endPoint.y - stratPoint.y);
        tDeltaY = dy != 0 ? Math.min(dy/(endPoint.y - stratPoint.y), 10000000.0f) :  10000000.0f;
        tMaxY = dy > 0 ? tDeltaY * MathUtils.fra1(stratPoint.y) : tDeltaY * MathUtils.fra0(stratPoint.y);

        dz = Math.signum(endPoint.z - stratPoint.z);
        tDeltaZ = dz != 0 ? Math.min(dz/(endPoint.z - stratPoint.z), 10000000.0f) :  10000000.0f;
        tMaxZ = dz > 0 ? tDeltaZ * MathUtils.fra1(stratPoint.z) : tDeltaZ * MathUtils.fra0(stratPoint.z);

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
                if(block != Material.AIR.getId()){
                    this.aimedBlock = block;
                    break;
                }
            }
        }
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
