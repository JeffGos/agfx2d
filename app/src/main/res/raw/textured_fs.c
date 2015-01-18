precision lowp float;

uniform lowp vec4 color;
uniform lowp sampler2D texture;

varying highp vec2 tCoordOut;

void main()
{
    gl_FragColor = color * texture2D(texture, tCoordOut);
}