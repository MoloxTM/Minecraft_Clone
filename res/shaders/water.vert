#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;
layout (location = 2) in float aBlockID;
layout (location = 3) in float aBlockFace;
layout (location = 4) in float aOcclusion;

out vec2 textureCoord;
out float blockID;
out float blockFace;
out vec3 toCameraVector;
out vec4 clipSpace;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform vec3 cameraPosition;


void main() {
    vec4 worldPosition = model * vec4(aPosition, 1.0);
    gl_Position = projection * view * worldPosition;
    clipSpace = gl_Position;
    textureCoord = aTexture;
    blockID=aBlockID;
    blockFace=aBlockFace;
    toCameraVector = cameraPosition - worldPosition.xyz;
}