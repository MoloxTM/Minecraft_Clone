#version 330 core

out vec4 FragColor;

in vec2 textureCoord;

uniform sampler2D uTexture;

void main() {
    FragColor = texture(uTexture, textureCoord);
}