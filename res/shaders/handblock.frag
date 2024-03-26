#version 330 core

in vec2 textureCoords;
in float face;

out vec4 FragColor;

uniform sampler2D uTexture;
uniform float blockID;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoords).a <= 0) discard;
    vec3 tex = texture(uTexture, textureCoords).rgb;
    float opacity = 1;

    if (equals(face, 1.0f)) {
        if (equals(blockID, 3.0f)) {
            tex.rgb *= vec3(0.33 + 0.086, 0.66 + 0.037, 0.15);
        }
    }

    FragColor = vec4(tex, opacity);
}