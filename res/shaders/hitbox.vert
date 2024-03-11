#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;

out vec2 textureCoords;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

void main() {
    gl_Position = projection * view * model * vec4(aPosition.x, aPosition.y, -10.0f, 1.0);
    textureCoords = aTexture;
}