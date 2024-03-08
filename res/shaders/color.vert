#version 330 core

layout (location = 0) in vec2 aPosition;
layout (location = 2) in float aIndex;

uniform mat4 projection;
uniform mat4 model;

uniform float depth;
uniform float width;
uniform float height;
uniform float x;
uniform float y;

uniform vec2 tex0, tex1, tex2, tex3;

bool equals(float a, float b) {
    return abs(a - b) < 1e-5;
}

void main() {

    vec2 texturePosition;
    vec2 position = aPosition.xy;

    position.x = x;
    position.y = y;

    if (equals(aIndex, 0.0f)) {
        texturePosition = tex0;
    } else if (equals(aIndex, 1.0f)) {
        position.y += height;
        texturePosition = tex1;
    } else if (equals(aIndex, 2.0f)) {
        position.x += width;
        position.y += height;
        texturePosition = tex2;
    } else if (equals(aIndex, 3.0f)) {
        position.x += width;
        texturePosition = tex3;
    }

    gl_Position = projection * model * vec4(position.x, position.y, depth, 1.0);
}