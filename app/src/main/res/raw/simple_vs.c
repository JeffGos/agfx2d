uniform mat4 vpMatrix;
uniform mat4 mMatrix;
attribute vec4 vPosition;

void main() {
    gl_Position = mMatrix * vpMatrix * vPosition;
}
