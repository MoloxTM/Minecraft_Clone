#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in float blockID;
in float blockFace;

uniform sampler2D uTexture;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    vec3 tex = texture(uTexture, textureCoord).rgb;

    if (equals(blockID, 7.0f)) {
        tex.rgb *= vec3(0.5, 0.75, 0.4);
    }

    if(equals(blockID, 3.0f) && equals(blockFace, 2.0f)){
        tex.rgb *= vec3(0.5, 0.75, 0.4);
    }


    FragColor = vec4(tex,1);
}