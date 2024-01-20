#version 330 core

out vec4 FragColor;

in vec3 textureCoords;

uniform samplerCube uTexture;

void main() {
    FragColor = texture(uTexture, textureCoords);
}