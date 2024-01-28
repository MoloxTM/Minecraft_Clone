#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in float blockID;
in float blockFace;
in float brightnessFace;

uniform sampler2D uTexture;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoord).a <= 0) discard;
    vec3 tex = texture(uTexture, textureCoord).rgb;
    float opacity = 1;
    if (equals(blockID, 7.0f) || equals(blockID, 8.0f)) {
        tex.rgb *= vec3(0.5, 0.75, 0.4);
    }

    if(equals(blockID, 3.0f) && equals(blockFace, 2.0f)){
        tex.rgb *= vec3(0.5, 0.75, 0.4);
    }

    tex.rgb *= brightnessFace;

    FragColor = vec4(tex, opacity);
}