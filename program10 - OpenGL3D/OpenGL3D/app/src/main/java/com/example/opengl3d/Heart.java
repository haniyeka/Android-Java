package com.example.opengl3d;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Heart {

    private int mProgramObject;
    private int mMVPMatrixHandle;
    private int mColorHandle;
    private FloatBuffer mVertices;

    float size = 0.1f;
    float width = 1f;

    //this is the initial data, which will need to translated into the mVertices variable in the consturctor.
    float[] mVerticesData = new float[]{
            ////////////////////////////////////////////////////////////////////
            // FRONT
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            //1
            -3.125f,0.092f,0.5f,
            -1.257f,1.668f,0.5f,
            0,0.092f,1,

            -3.125f,0.092f,0.5f,
            0,0.092f,1,
            -1.562f,-1.761f,0.5f,

            0,0.092f,1,
            -1.562f,-1.761f,0.5f,
            0,-1.76f,1,

            -1.562f,-1.761f,0.5f,
            0,-1.76f,1,
            0,-3.611f,0.5f,

            0,-1.76f,1,
            1.562f,-1.46f,0.5f,
            0,-3.611f,0.5f,

            0,-1.76f,1,
            0,0.092f,1,
            1.562f,-1.46f,0.5f,

            0,0.092f,1,
            3.126f,0.092f,0.5f,
            1.562f,-1.46f,0.5f,

            0,0.092f,1,
            1.257f,1.668f,0.5f,
            3.126f,0.092f,0.5f,

            //
            -3.125f,0.092f,0,
            -1.257f,1.668f,0,
            0,0.092f,-0.5f,

            -3.125f,0.092f,0,
            0,0.092f,-0.5f,
            -1.562f,-1.761f,0,

            0,0.092f,-0.5f,
            -1.562f,-1.761f,0,
            0,-1.76f,-0.5f,

            -1.562f,-1.761f,0,
            0,-1.76f,-0.5f,
            0,-3.611f,0,

            0,-1.76f,-0.5f,
            1.562f,-1.46f,0,
            0,-3.611f,0,

            0,-1.76f,-0.5f,
            0,0.092f,-0.5f,
            1.562f,-1.46f,0,

            0,0.092f,-0.5f,
            3.126f,0.092f,0,
            1.562f,-1.46f,0,

            0,0.092f,-0.5f,
            1.257f,1.668f,0,
            3.126f,0.092f,0,

            //square 1
            -3.125f,0.092f,0,
            -3.125f,0.092f,0.5f,
            -1.562f,-1.761f,0,

            -3.125f,0.092f,0.5f,
            -1.562f,-1.761f,0.5f,
            -1.562f,-1.761f,0,
            //square 2
            -1.562f,-1.761f,0,
            -1.562f,-1.761f,0.5f,
            0,-3.611f,0,

            -1.562f,-1.761f,0.5f,
            0,-3.611f,0.5f,
            0,-3.611f,0,
            //square 3
            0,-3.611f,0,
            0,-3.611f,0.5f,
            1.562f,-1.46f,0,

            0,-3.611f,0.5f,
            1.562f,-1.46f,0.5f,
            1.562f,-1.46f,0,
            //square 4

            1.562f,-1.46f,0,
            1.562f,-1.46f,0.5f,
            3.126f,0.092f,0,

            1.562f,-1.46f,0.5f,
            3.126f,0.092f,0.5f,
            3.126f,0.092f,0,
            //square 5

            3.126f,0.092f,0,
            3.126f,0.092f,0.5f,
            1.257f,1.668f,0,

            3.126f,0.092f,0.5f,
            1.257f,1.668f,0.5f,
            1.257f,1.668f,0,

            //square 6
            1.257f,1.668f,0,
            1.257f,1.668f,0.5f,
            0,0.092f,-0.5f,

            1.257f,1.668f,0.5f,
            0,0.092f,1,
            0,0.092f,-0.5f,
            //square 7
            0,0.092f,1,
            0,0.092f,-0.5f,
            -1.257f,1.668f,0,

            0,0.092f,1,
            -1.257f,1.668f,0.5f,
            -1.257f,1.668f,0,
            //square 8
            -1.257f,1.668f,0.5f,
            -1.257f,1.668f,0,
            -3.125f,0.092f,0,

            -1.257f,1.668f,0.5f,
            -3.125f,0.092f,0,
            -3.125f,0.092f,0.5f,
    };

    float colorcyan[] = myColor.cyan();
    float colorblue[] = myColor.blue();
    float colorred[] = myColor.red();
    float colorgray[] = myColor.gray();
    float colorgreen[] = myColor.green();
    float coloryellow[] = myColor.yellow();
    float colorpink[] = myColor.pink();
    float colorDarkpink[] = myColor.pinkDark();
    float colorlightgray[] = myColor.lightgray();

    //vertex shader code
    String vShaderStr =
            "#version 300 es 			  \n"
                    + "uniform mat4 uMVPMatrix;     \n"
                    + "in vec4 vPosition;           \n"
                    + "void main()                  \n"
                    + "{                            \n"
                    + "   gl_Position = uMVPMatrix * vPosition;  \n"
                    + "}                            \n";
    //fragment shader code.
    String fShaderStr =
            "#version 300 es		 			          	\n"
                    + "precision mediump float;					  	\n"
                    + "uniform vec4 vColor;	 			 		  	\n"
                    + "out vec4 fragColor;	 			 		  	\n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = vColor;                    	\n"
                    + "}                                            \n";

    String TAG = "Cube";


    //finally some methods
    //constructor
    public Heart() {
        for(int i = 0; i<mVerticesData.length; i++){
            mVerticesData[i] = mVerticesData[i]/4;
        }
        //first setup the mVertices correctly.
        mVertices = ByteBuffer
                .allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVerticesData);
        mVertices.position(0);

        //setup the shaders
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        // Load the vertex/fragment shaders
        vertexShader = myRendererStar.LoadShader(GLES30.GL_VERTEX_SHADER, vShaderStr);
        fragmentShader = myRendererStar.LoadShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr);

        // Create the program object
        programObject = GLES30.glCreateProgram();

        if (programObject == 0) {
            Log.e(TAG, "So some kind of error, but what?");
            return;
        }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);

        // Bind vPosition to attribute 0
        GLES30.glBindAttribLocation(programObject, 0, "vPosition");

        // Link the program
        GLES30.glLinkProgram(programObject);

        // Check the link status
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }

        // Store the program object
        mProgramObject = programObject;

        //now everything is setup and ready to draw.
    }

    public void draw(float[] mvpMatrix) {

        // Use the program object
        GLES30.glUseProgram(mProgramObject);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramObject, "uMVPMatrix");
        myRendererStar.checkGlError("glGetUniformLocation");

        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(mProgramObject, "vColor");


        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        myRendererStar.checkGlError("glUniformMatrix4fv");

        int VERTEX_POS_INDX = 0;
        mVertices.position(VERTEX_POS_INDX);  //just in case.  We did it already though.

        //add all the points to the space, so they can be correct by the transformations.
        //would need to do this even if there were no transformations actually.
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT,
                false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);

        //Now we are ready to draw the cube finally.
        int startPos =0;
        int verticesPerface = 3;

        //draw front face
        //1
        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //2
        GLES30.glUniform4fv(mColorHandle, 1, colorpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
//      //3
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //4
        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //5
        GLES30.glUniform4fv(mColorHandle, 1, colorpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //6
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7

        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //9
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //10
        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //11
        GLES30.glUniform4fv(mColorHandle, 1, colorpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //12
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //13
        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //14
        GLES30.glUniform4fv(mColorHandle, 1, colorpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //15
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //16
        GLES30.glUniform4fv(mColorHandle, 1, colorDarkpink, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;
        //17

        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //18
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //19
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //20
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, 6);
        startPos += 6;

        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //18
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //19
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,6);
        startPos += 6;
        //20
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, 6);
        startPos += 6;
    }
}
