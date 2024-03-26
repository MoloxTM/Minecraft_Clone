#version 330

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;
layout (location = 2) in float aIndex;
layout (location = 3) in float aFace;

out vec2 textureCoords;
out float face;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec2 texPx0, texPx1, texPx2, texPx3;
uniform vec2 texNx0, texNx1, texNx2, texNx3;

uniform vec2 texPy0, texPy1, texPy2, texPy3;
uniform vec2 texNy0, texNy1, texNy2, texNy3;

uniform vec2 texPz0, texPz1, texPz2, texPz3;
uniform vec2 texNz0, texNz1, texNz2, texNz3;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {

    vec2 texturePosition;
    vec2 tex0, tex1, tex2, tex3;

    if (equals(aFace, 0.0f)) {
        tex0 = texPx0;
        tex1 = texPx1;
        tex2 = texPx2;
        tex3 = texPx3;
    } else if (equals(aFace, 1.0f)) {
        tex0 = texPy0;
        tex1 = texPy1;
        tex2 = texPy2;
        tex3 = texPy3;
    } else if (equals(aFace, 2.0f)) {
        tex0 = texPz0;
        tex1 = texPz1;
        tex2 = texPz2;
        tex3 = texPz3;
    } else if (equals(aFace, 3.0f)) {
        tex0 = texNx0;
        tex1 = texNx1;
        tex2 = texNx2;
        tex3 = texNx3;
    } else if (equals(aFace, 4.0f)) {
        tex0 = texNy0;
        tex1 = texNy1;
        tex2 = texNy2;
        tex3 = texNy3;
    } else if (equals(aFace, 5.0f)) {
        tex0 = texNz0;
        tex1 = texNz1;
        tex2 = texNz2;
        tex3 = texNz3;
    }

    if (equals(aIndex, 0.0f)) {
        texturePosition = tex0;
    } else if (equals(aIndex, 1.0f)) {
        texturePosition = tex1;
    } else if (equals(aIndex, 2.0f)) {
        texturePosition = tex2;
    } else if (equals(aIndex, 3.0f)) {
        texturePosition = tex3;
    }

    gl_Position = projection * view * model * vec4(aPosition, 1.0);
    textureCoords = texturePosition;
    face = aFace;
}