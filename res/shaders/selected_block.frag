#version 330 core

out vec4 FragColor;

in vec2 textureCoords;

uniform sampler2D uTexture;
uniform float spriteIndex;

void main() {
    FragColor = texture(uTexture, vec2(textureCoords.x + spriteIndex * 1.0f / 16.0f, textureCoords.y));
}