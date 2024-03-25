#version 330 core

out vec4 FragColor;

in vec2 textureCoord;

uniform sampler2D uTexture;
uniform float hit;

void main() {
    if (hit != 0.0f) {
        vec3 texRgb = texture(uTexture, textureCoord).rgb;
        texRgb *= vec3(3.0, 1.0, 1.0);
        FragColor = vec4(texRgb, 1.0);
    } else {
        FragColor = texture(uTexture, textureCoord);
    }
}