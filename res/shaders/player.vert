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
    if (aPartId != 1.0)
        return mat4(1.0f);

    return mat4(
        1,     0     ,      0      ,   0,
        0, cos(angle), -sin(angle) ,   0,
        0, sin(angle), cos(angle)  ,   0,
        0,     0     ,      0      ,   1
    );
}

mat4 rotateX(float angle) {
    if (aPartId != 1.0)
        return mat4(1.0f);

    return mat4(
        cos(angle),  0, sin(angle), 0,
        0,           1, 0,          0,
        -sin(angle), 0, cos(angle), 0,
        0,           0, 0,          1
    );
}

void main() {
    gl_Position = projection * view * model * rotateX(yaw) * rotateY(pitch) * vec4(aPosition, 1.0);
    textureCoord = aTexture;
}