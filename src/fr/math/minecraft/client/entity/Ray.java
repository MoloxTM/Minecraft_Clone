package fr.math.minecraft.client.entity;

import fr.math.minecraft.client.meshs.Face;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Ray {

    private Chunk aimedChunk;
    private byte aimedBlock;
    private Vector3i blockWorldPosition, blockChunkPosition;
    protected final float reach;
    protected final int xAxis = 0;
    protected final int yAxis= 1;
    protected final int zAxis = 2;
    private Face faceAimed;
    private final static Logger logger = LoggerUtility.getClientLogger(Ray.class, LogType.TXT);

    public Ray(float reach) {
        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();
        this.blockWorldPosition = new Vector3i();
        this.blockChunkPosition = new Vector3i();
        this.reach = reach;
        this.faceAimed = null;
    }

    public void update(Vector3f position, Vector3f front, World world, boolean isServer) {

        int direction = 0;
        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();

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
                direction = xAxis;
            } else if (tMaxY < tMaxX && tMaxY < tMaxZ) {
                rayPosition.y += dy;
                tMaxY += tDeltaY;
                direction = yAxis;
            } else {
                rayPosition.z += dz;
                tMaxZ += tDeltaZ;
                direction = zAxis;
            }

            synchronized (world.getChunks()) {
                this.aimedChunk = world.getChunkAt(rayPosition.x, rayPosition.y, rayPosition.z);

                if (this.aimedChunk == null && isServer) {
                    System.out.println("Génération d'un chunk...");
                    Vector3i chunkPos = Utils.getChunkPosition(rayPosition.x, rayPosition.y, rayPosition.z);
                    this.aimedChunk = new Chunk(chunkPos.x, chunkPos.y, chunkPos.z);
                    this.aimedChunk.generate(world, world.getTerrainGenerator());
                    world.addChunk(this.aimedChunk);
                }


                if (this.aimedChunk != null) {
                    int blockX = rayPosition.x % Chunk.SIZE;
                    int blockY = rayPosition.y % Chunk.SIZE;
                    int blockZ = rayPosition.z % Chunk.SIZE;

                    blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
                    blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
                    blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

                    byte block = this.aimedChunk.getBlock(blockX, blockY, blockZ);

                    //System.out.println("Ray position " + rayPosition + " aimedChunk " + aimedChunk + " " + aimedChunk.getPosition());
                    //System.out.println("localX " + blockX + " localY " + blockY + " localZ " + blockZ + " " + Material.getMaterialById(block));

                    if (block != Material.AIR.getId() && block != Material.WATER.getId()) {
                        this.blockWorldPosition.x = rayPosition.x;
                        this.blockWorldPosition.y = rayPosition.y;
                        this.blockWorldPosition.z = rayPosition.z;
                        this.blockChunkPosition.x = blockX;
                        this.blockChunkPosition.y = blockY;
                        this.blockChunkPosition.z = blockZ;

                        this.setFace(dx, dy, dz, direction);
                        this.aimedBlock = block;
                        break;
                    }
                }
            }

        }
    }

    public void setFace(float dx, float dy, float dz, int direction) {
        switch (direction) {
            case xAxis :
                if(dx > 0) {
                    this.faceAimed = Face.WEST;
                } else {
                    this.faceAimed= Face.EST;
                }
                break;
            case yAxis:
                if(dy > 0) {
                    this.faceAimed= Face.DOWN;
                } else {
                    this.faceAimed = Face.UP;
                }
                break;
            case zAxis:
                if(dz > 0) {
                    this.faceAimed= Face.SOUTH;
                } else {
                    this.faceAimed= Face.NORTH;
                }
                break;
        }
    }

    public Vector3i getBlockPlacedPosition(Vector3i blockWorldPosition) {
        Vector3i block = new Vector3i(blockWorldPosition);

        return block.add(faceAimed.getNormal());
    }

    public Vector3i getBlockChunkPositionLocal() {
        return blockChunkPosition;
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

    public void reset() {
        this.aimedChunk = null;
        this.aimedBlock = Material.AIR.getId();
        this.blockChunkPosition = new Vector3i();
        this.blockWorldPosition = new Vector3i();
    }

    public boolean isAimingBlock() {
        return aimedChunk != null && aimedBlock != Material.AIR.getId() && aimedBlock != Material.WATER.getId();
    }

}
