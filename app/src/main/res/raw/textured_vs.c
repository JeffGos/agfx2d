attribute vec4 vPosition;
attribute vec2 tCoordIn;

uniform mat4 vpMatrix;

uniform vec4 scale;
uniform vec4 preRotationTranslation;
uniform mat4 rotation;
uniform vec4 translation;

uniform vec2 tCoordScale;
uniform vec2 tCoordOffset;

varying vec2 tCoordOut;

void main()
{
    gl_Position = (vPosition * scale) + preRotationTranslation;
    gl_Position = gl_Position * rotation;
    gl_Position = gl_Position - preRotationTranslation + translation;
    gl_Position = gl_Position * vpMatrix;

    tCoordOut = tCoordIn * tCoordScale + tCoordOffset;
 }