#version 330 core

in vec2 textureCoords;
in vec3 color;

uniform sampler2D uTexture;

out vec4 FragColor;

void main() {
    if (texture(uTexture, textureCoords).a <= 0)
        discard;
    float c = texture(uTexture, textureCoords).r;
    FragColor = vec4(c, c, c, 1) * vec4(color, 1);
}