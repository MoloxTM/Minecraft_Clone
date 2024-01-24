#version 330 core

out vec4 FragColor;

in vec2 textureCoord;
in float blockID;
in float blockFace;

uniform sampler2D uTexture;

void main() {
    vec3 tex = texture(uTexture, textureCoord).rgb;
    if(blockID==7.0f){
        tex.rbg*=vec3(0.6320, 1.0180, 0.3380);
    }

    if(blockID==3.0f && blockFace==2.0f){
        tex.rgb*=vec3(1, 1, 1);
    }


    FragColor = vec4(tex,1);
}