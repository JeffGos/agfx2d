attribute vec4 vPosition;

uniform mat4 vpMatrix;

uniform vec4 scale;
uniform vec4 preRotationTranslation;
uniform mat4 rotation;
uniform vec4 translation;

void main()
{
    gl_Position = (vPosition * scale) + preRotationTranslation;
    gl_Position = gl_Position * rotation;
    gl_Position = gl_Position - preRotationTranslation + translation;
    gl_Position = gl_Position * vpMatrix;
 }