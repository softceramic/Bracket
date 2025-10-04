#version 330

in vec3 outColour;
out vec4 fragColour;

void main(){
    fragColour = vec4(outColour,1.0);
}