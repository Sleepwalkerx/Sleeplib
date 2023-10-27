#version 110

layout(location=0) in vec4 vertex;
out vec2 pos;   // screen position <-1,+1>
void main(){
    pos = vertex.xy;
    gl_Position = vertex;
}