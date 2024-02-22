#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in vec4 clipSpace;
in vec3 toCameraVector;

uniform sampler2D uTexture;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;


bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoord).a <= 0) discard;


    vec3 viewVector = normalize(toCameraVector);
    vec3 faceNormal = vec3(0.0, 1.0, 0.0);
    float fresnel = dot(viewVector, faceNormal);

    vec4 tex = texture(uTexture, textureCoord);

    vec4 teinte = vec4(33/255.0, 63/255.0, 153/255.0, 1.0);

    FragColor = mix(teinte, tex, fresnel);
}