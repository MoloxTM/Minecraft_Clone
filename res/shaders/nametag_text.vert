#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTexture;

mat4 rotateX(float angle) {
    return mat4(
        cos(angle),  0, sin(angle), 0,
        0,           1, 0,          0,
        -sin(angle), 0, cos(angle), 0,
        0,           0, 0,          1
    );
}

out vec3 color;
out vec2 textureCoords;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform float yaw;

void main() {
    gl_Position = projection * view * model * rotateX(yaw) * vec4(aPosition, 1.0);
    color = aColor;
    textureCoords = aTexture;
}