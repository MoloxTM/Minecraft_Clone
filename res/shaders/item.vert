#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;

out vec2 textureCoords;

uniform mat4 projection;
uniform mat4 model;
uniform float yTranslation;
uniform float rotationAngleX;

mat4 translate(float x, float y, float z) {
    return mat4(
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        x, y, z, 1
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

void main() {
    gl_Position = projection * model * rotateX(rotationAngleX) * translate(0, yTranslation, 0) * vec4(aPosition, 1.0);
    textureCoords = aTexture;
}