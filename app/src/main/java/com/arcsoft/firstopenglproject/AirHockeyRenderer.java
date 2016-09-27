package com.arcsoft.firstopenglproject;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.arcsoft.firstopenglproject.util.Shaderhelper;
import com.arcsoft.firstopenglproject.util.TextResourceReader;

import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_PACK_ALIGNMENT;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int BYTE_PER_FLOAT = 4;

    private FloatBuffer vertexData;

    private Context context;

    private int program;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    public AirHockeyRenderer(Context context) {

        this.context = context;

        float[] tableVertices = {
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                -0.5f, 0f,
                0.5f, 0f,

                0f, -0.25f,
                0f, 0.25f

        };

        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTE_PER_FLOAT).
                order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexData.put(tableVertices);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.sample_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.sample_fragment_shader);

        int vertexShader = Shaderhelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = Shaderhelper.compileFragmentShader(fragmentShaderSource);

        program = Shaderhelper.linkProgram(vertexShader,fragmentShader);

        Shaderhelper.validateProgram(program);


        glUseProgram(program);

        uColorLocation = glGetUniformLocation(program,U_COLOR);
        aPositionLocation = glGetAttribLocation(program,A_POSITION);

        vertexData.position(0);

        glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,vertexData);

        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        glDrawArrays(GL_TRIANGLES,0,6);

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_LINES,6,2);

        glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        glDrawArrays(GL_POINTS,8,1);

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_POINTS,9,1);
    }
}
