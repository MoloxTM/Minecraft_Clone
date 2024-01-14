#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexture;
layout (location = 2) in float aPartId;

out vec2 textureCoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform float yaw;
uniform float pitch;
uniform float time;
uniform float handRotation;
uniform float bodyYaw;


mat4 rotateY(float angle) {
    return mat4(
        1,     0     ,      0      ,   0,
        0, cos(angle), -sin(angle) ,   0,
        0, sin(angle), cos(angle)  ,   0,
        0,     0     ,      0      ,   1
    );
}

mat4 rotateX(float angle) {
    return mat4(
        cos(angle),  0, sin(angle), 0,
        0,           1, 0,          0,
        -sin(angle), 0, cos(angle), 0,
        0,           0, 0,          1
    );
}

mat4 rotateZ(float angle) {
    return mat4(
        cos(angle),  -sin(angle),  0, 0,
        sin(angle),   cos(angle),  0, 0,
        0,       0,            1,  0,
        0,       0,            0,  1
    );
}

mat4 translate(float x, float y, float z) {
    return mat4(
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        x, y, z, 1
    );
}

mat4 rotateAroundPoint(float angle, vec3 pivot) {
    mat4 toOrigin = translate(-pivot.x, -pivot.y, -pivot.z);
    mat4 rotation = rotateY(angle);
    mat4 fromOrigin = translate(pivot.x, pivot.y, pivot.z);

    return fromOrigin * rotation * toOrigin;
}

void main() {
    mat4 translationMatrix = mat4(1.0f);
    vec3 position = aPosition;

    // Make body follows head in case the head's yaw is too far away from the body
    if (aPartId != 1.0) {
        translationMatrix *= rotateX(yaw);
    }

    if (aPartId == 1.0) {
        // If vertex is a part of head
        vec3 pivot = vec3(-0.25f, -0.25f, 0.0f);
        translationMatrix *= rotateX(yaw) * rotateAroundPoint(pitch, pivot) * translate(0, -0.125 * abs(sin(pitch)), 0);
    } else if (aPartId == 3.0) {
        // If vertex is a part of right hand
        float rotationAngle = sin((position.z + time) * .8f) / 32.0f;
        vec3 pivot = vec3(-0.25f, -0.25f, 0.0f);
        translationMatrix *= rotateZ(rotationAngle) * rotateAroundPoint(handRotation, pivot);
    } else if (aPartId == 3.5) {
        // If vertex is a part of left hand
        vec3 pivot = vec3(0.25f, -0.25f, 0.0f);
        float rotationAngle = sin((position.z + time) * .8f) / 32.0f;
        translationMatrix *= rotateZ(-rotationAngle) * rotateAroundPoint(-handRotation, pivot);
    } else if (aPartId == 4.0) {
        // If vertex is a part of left leg
        vec3 pivot = vec3(0.0f, -1.1f, 0.0f);
        translationMatrix *= rotateAroundPoint(-handRotation, pivot);
    } else if (aPartId == 4.5) {
        // If vertex is a part of right leg
        vec3 pivot = vec3(0.0f, -1.1f, 0.0f);
        translationMatrix *= rotateAroundPoint(handRotation, pivot);
    }

    gl_Position = projection * view * model * translationMatrix * vec4(position, 1.0);
    textureCoord = aTexture;
}