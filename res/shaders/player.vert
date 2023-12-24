#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;
layout (location = 2) in float aPartId;

out vec2 textureCoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform float yaw;
uniform float pitch;

mat4 rotateY(float angle) {
    return mat4(
        1,     0     ,      0      ,   0,
        0, cos(angle), -sin(angle) ,   0,
        0, sin(angle), cos(angle)  ,   0,
        0,     0     ,      0      ,   1
    );
}

mat4 rotateX(float angle) {
    return mat4(
        cos(angle),  0, sin(angle), 0,
        0,           1, 0,          0,
        -sin(angle), 0, cos(angle), 0,
        0,           0, 0,          1
    );
}

mat4 translate(float x, float y, float z) {
    return mat4(
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        x, y, z, 1
    );
}

void main() {
    mat4 translationMatrix = mat4(1.0f);

    // If vertex is a part of head
    if (aPartId == 1.0) {
        float ty = pitch > 0 ? -0.25 * sin(pitch) / 2 : 0.25 * sin(pitch) / 2;
        float tz;
        if (yaw > 0)
            tz = pitch < 0 ? 0.25 * sin(pitch) : 0;
        else
            tz = pitch < 0 ? -0.25 * sin(pitch) : 0;

        translationMatrix =  translate(0, ty, tz) * rotateX(yaw) * rotateY(pitch);
    }

    gl_Position = projection * view * model * translationMatrix * vec4(aPosition, 1.0);
    textureCoord = aTexture;
}