#version 330

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec3 colour;

out vec3 outColour;

uniform mat4 projectionmatrix;
uniform mat4 modelmatrix;

void main(){
    gl_Position = projectionmatrix * modelmatrix * vec4(inPosition, 1.0);
    outColour = colour;
}