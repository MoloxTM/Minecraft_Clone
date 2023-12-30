#version 330 core

layout (location = 0) in vec3 aPosition;

mat4 rotateX(float angle) {
    return mat4(
    cos(angle),  0, sin(angle), 0,
    0,           1, 0,          0,
    -sin(angle), 0, cos(angle), 0,
    0,           0, 0,          1
    );
}

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform float yaw;

void main() {
    gl_Position = projection * view * model * rotateX(yaw) * vec4(aPosition, 1.0);
}