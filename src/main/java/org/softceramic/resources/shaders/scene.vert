#version 330

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec3 colour;

out vec3 outColour;

uniform mat4 projectionmatrix;

void main(){
    gl_Position = projectionmatrix * vec4(inPosition, 1.0);
    outColour = colour;
}