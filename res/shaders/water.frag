#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in vec4 clipSpace;
in vec3 toCameraVector;

uniform sampler2D uTexture;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoord).a <= 0) discard;

    vec3 viewVector = normalize(toCameraVector);
    vec3 faceNormal = vec3(0.0, 1.0, 0.0);
    float fresnel = dot(viewVector, faceNormal);

    vec3 reflected = reflect(viewVector, faceNormal);

    vec4 tex = vec4(texture(uTexture, textureCoord).rgb * 0.6, .9);
    vec4 teinte = vec4(0.0, 0.0, 0.3, 0.3);


    FragColor = mix(tex, teinte, fresnel);
}