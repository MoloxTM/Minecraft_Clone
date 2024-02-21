#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in float blockID;
in float blockFace;
in float brightnessFace;
in vec4 clipSpace;
in vec3 toCameraVector;

uniform sampler2D uTexture;
uniform float biome;
uniform float r, g, b;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;

uniform float moveFactor;


bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {
    if (texture(uTexture, textureCoord).a <= 0) discard;

    vec2 ndc = (clipSpace.xy/clipSpace.w)/2.0 + 0.5;
    vec2 refractTextCoords = vec2(ndc.x, ndc.y);
    vec2 reflectTextCoords = vec2(ndc.x, -ndc.y);

    refractTextCoords = clamp(refractTextCoords, 0.001, 0.999);

    reflectTextCoords.x = clamp(reflectTextCoords.x, 0.001, 0.999);
    reflectTextCoords.y = clamp(reflectTextCoords.y, -0.999, -0.001);

    vec4 reflectColour = texture(reflectionTexture, reflectTextCoords);
    vec4 refractColour = texture(refractionTexture, refractTextCoords);

    vec3 viewVector = normalize(toCameraVector);
    float refractiveFactor = dot(viewVector, vec3(0.0, 1.0, 0.0));


    float opacity = 1;
    vec3 tex = texture(uTexture, textureCoord).rgb;

    FragColor = mix(reflectColour, refractColour, refractiveFactor);
    FragColor = mix(FragColor, vec4(0.0, 0.3, 0.5, 1.0), 0.2);

}