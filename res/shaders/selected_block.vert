#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;

out vec2 textureCoords;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform float scale;


void main() {
    vec3 position = aPosition * scale;
    gl_Position = projection * view * model * vec4(position, 1.0);
    textureCoords = aTexture;
}