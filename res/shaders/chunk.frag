#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in float blockID;
in float blockFace;
in float brightnessFace;

uniform sampler2D uTexture;
uniform float biome;

uniform float r, g, b;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoord).a <= 0) discard;
    vec3 tex = texture(uTexture, textureCoord).rgb;
    float opacity = 1;

    if (equals(blockID, 7.0f) || equals(blockID, 12.0f) || equals(blockID, 53.0f)) {
        if (biome == 0) {
            tex.rgb *= vec3(0.64, 0.61, 0.36);
        } else if (biome == 1) {
            tex.rgb *= vec3(0.33, 0.66, 0.17);
        } else if (biome == 2) {
            tex.rgb *= vec3(0.39, 0.66, 0.28);
        } else if (biome == 3) {
            tex.rgb *= vec3(0.33 + 0.086, 0.66 + 0.037, 0.15);
        } else {
            tex.rgb *= vec3(100, 100, 100);
        }
    }

    if((equals(blockID, 3.0f) && equals(blockFace, 2.0f)) || equals(blockID, 8.0f)){
        if(biome == 0) {
            tex.rgb *= vec3(0.71, 0.68, 0.32);
        } else if(biome == 1) {
            tex.rgb *= vec3(0.48, 0.73, 0.36);
        } else if(biome == 2) {
            tex.rgb *= vec3(0.511, 0.755, 0.3591);
        } else if(biome == 3) {
            tex.rgb *= vec3(0.511, 0.755, 0.3591);
        } else {
            tex.rgb *= vec3(100, 100, 100);
        }
    }

    if(equals(blockID, 8.0f)){
        if(biome == 0) {
            tex.rgb *= vec3(0.71, 0.68, 0.32);
        } else if(biome == 1) {
            tex.rgb *= vec3(0.48, 0.73, 0.36);
        } else if(biome == 2) {
            tex.rgb *= vec3(1.55, 1.55, 1.55);
        } else if(biome == 3) {
            tex.rgb *= vec3(1 + 0.38, 1 + 0.38, 1 + 0.35);
        } else {
            tex.rgb *= vec3(100, 100, 100);
        }
    }

    tex.rgb *= brightnessFace;

    FragColor = vec4(tex, opacity);
}