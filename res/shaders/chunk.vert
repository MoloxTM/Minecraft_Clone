#version 330 core

layout (location = 0) in float packedData;
layout (location = 1) in vec2 aTexture;

float x, y, z;
int aBlockID;
int aBlockFace;

out vec2 textureCoord;
out float blockID;
out float blockFace;
out float brightnessFace;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

/*
x : 4 bits, y : 4 bits, z : 4 bits, blockId : 8 bits, blockFace : 3 bits, occlusion : 2 bits
 */
void unpack(float packedDataFloat) {
    int packedData = floatBitsToInt(packedDataFloat);
    int xSize = 6, ySize = 6, zSize = 6, blockIdSize = 8, blockFaceSize = 3;
    int xMask = 63, yMask = 63, zMask = 63, blockIdMask = 255, blockFaceMask = 7;

    float extractedX = packedData >> (ySize + zSize + blockIdSize + blockFaceSize);
    float extractedY = packedData >> (zSize + blockIdSize + blockFaceSize) & yMask;
    float extractedZ = packedData >> (blockIdSize + blockFaceSize) & zMask;

    aBlockID = int((packedData >> (blockFaceSize)) & blockIdMask);
    aBlockFace = int(packedData & blockFaceMask);

    int packFactor = 64 / (16 + 1);

    x = (extractedX / packFactor) - 0.5f;
    y = (extractedY / packFactor) - 0.5f;
    z = (extractedZ / packFactor) - 0.5f;
}

float brigthness[6] = float[6](0.85f, 0.6f, 1.0f, 0.6f, 0.85f, 0.6f); /*[px, nx, py, ny, pz, nz]*/

void main() {
    unpack(packedData);
    vec3 aPosition = vec3(x, y, z);
    gl_Position = projection * view * model * vec4(aPosition, 1.0);
    textureCoord = aTexture;
    blockID = aBlockID;
    blockFace = aBlockFace;
    brightnessFace = brigthness[int(blockFace)];
}