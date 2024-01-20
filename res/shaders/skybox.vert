#version 330 core

layout (location = 0) in vec3 aPosition;

out vec3 textureCoords;

uniform mat4 projection;
uniform mat4 view;

void main() {
    vec4 position = projection * view * vec4(aPosition, 1.0);
    gl_Position = position.xyww;

    textureCoords = vec3(aPosition.x, -aPosition.y, aPosition.z);
}