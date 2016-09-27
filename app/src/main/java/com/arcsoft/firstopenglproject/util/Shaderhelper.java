package com.arcsoft.firstopenglproject.util;

import android.support.v4.util.LogWriter;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Shaderhelper {

    private static final String TAG = "Shaderhelper";

    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER,shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER,shaderCode);
    }

    private static int compileShader(int type,String shaderCode){

        final int shaderObjectId = glCreateShader(type);

        if(shaderObjectId==0){
            if(LoggerConfig.ON){
                Log.w(TAG,"could not create new shader");
            }

            return 0;
        }

        glShaderSource(shaderObjectId,shaderCode);

        glCompileShader(shaderObjectId);

        final int[] compileStatue = new int[1];
        glGetShaderiv(shaderObjectId,GL_COMPILE_STATUS,compileStatue,0);


        return 0;
    }
}
