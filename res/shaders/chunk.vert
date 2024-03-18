#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;
layout (location = 2) in float aBlockID;
layout (location = 3) in float aBlockFace;
layout (location = 4) in float aOcclusion;

out vec2 textureCoord;
out float blockID;
out float blockFace;
out float brightnessFace;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform float time;
uniform float occlusionEnabled;

uniform vec3 cameraPosition;

float brigthness[6] = float[6](0.85f, 0.6f, 1.0f, 0.6f, 0.85f, 0.6f); /*[px, nx, py, ny, pz, nz]*/

float occlusion[4] = float[4](0.1f, 0.25f, 0.5f, 1);

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

vec4 calculatePosition() {
    vec3 position = aPosition.xyz;
    if (equals(aBlockID, 7.0f) && equals(occlusionEnabled, 1.0f)) {
        position.x += sin((time + position.y + position.z) * 1.5f) / 15.0f;
        position.z -= cos((time + position.x + position.y) * 1.5f) / 15.0f;
    }

    if (equals(aBlockID, 12.0f) && equals(occlusionEnabled, 1.0f)) {
        position.x += sin((time + position.y + position.z) * 1.5f) / 15.0f;
        position.z -= cos((time + position.x + position.y) * 1.5f) / 15.0f;
    }

    if (equals(aBlockID, 8.0f) && equals(occlusionEnabled, 1.0f)) {
        position.x += sin((time + position.y + position.z) * 1.5f) / 15.0f;
        position.z -= cos((time + position.x + position.y) * 1.5f) / 15.0f;
    }

    if (equals(aBlockID, 9.0f) && equals(occlusionEnabled, 1.0f)) {
        position.x += sin((time + position.y + position.z) * 1.5f) / 15.0f;
        position.z -= cos((time + position.x + position.y) * 1.5f) / 15.0f;
    }

    if (equals(aBlockID, 53.0f) && equals(occlusionEnabled, 1.0f)) {
        position.x += sin((time + position.y + position.z) * 1.5f) / 15.0f;
        position.z -= cos((time + position.x + position.y) * 1.5f) / 15.0f;
    }

    return vec4(position, 1.0);
}

void main() {
    gl_Position = projection * view * model * calculatePosition();
    textureCoord = aTexture;
    blockID=aBlockID;
    blockFace=aBlockFace;
    if (equals(occlusionEnabled, 1.0f)) {
        brightnessFace = brigthness[int(blockFace)] * occlusion[int(aOcclusion)];
    } else {
        brightnessFace = brigthness[int(blockFace)];
    }
}