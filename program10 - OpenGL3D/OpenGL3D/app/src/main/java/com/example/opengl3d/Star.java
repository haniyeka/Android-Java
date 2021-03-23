package com.example.opengl3d;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Star {

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
            //1
            -4.431f,1.122f,0.5f,
            -1.118f,1.122f,1,
            -1.806f,-0.301f,1,

            -4.431f,1.122f,0,
            -1.118f,1.122f,-0.5f,
            -1.806f,-0.301f,-0.5f,

            -4.431f,1.122f,0,
            -4.431f,1.122f,0.5f,
            -1.806f,-0.301f,1,

            -4.431f,1.122f,0,
            -1.806f,-0.301f,1,
            -1.806f,-0.301f,-0.5f,

            -4.431f,1.122f,0.5f,
            -4.431f,1.122f,0,
            -1.118f,1.122f,-0.5f,

            -4.431f,1.122f,0.5f,
            -1.118f,1.122f,1,
            -1.118f,1.122f,-0.5f,

            -1.806f,-0.301f,1,
            0,-1.279f,1,
            -3.128f,-3.027f,0.5f,

            -1.806f,-0.301f,-0.5f,
            0,-1.279f,-0.5f,
            -3.128f,-3.027f,0,

            -1.806f,-0.301f,1,
            -1.806f,-0.301f,-0.5f,
            -3.128f,-3.027f,0,

            -1.806f,-0.301f,1,
            -3.128f,-3.027f,0,
            -3.128f,-3.027f,0.5f,

            -3.128f,-3.027f,0.5f,
            -3.128f,-3.027f,0,
            0,-1.279f,-0.5f,

            -3.128f,-3.027f,0.5f,
            0,-1.279f,1,
            0,-1.279f,-0.5f,
            //3
            0,-1.279f,1,
            1.832f,-0.252f,1,
            3.242f,-3.035f, 0.5f,

            0,-1.279f,-0.5f,
            1.832f,-0.252f,-0.5f,
            3.242f,-3.035f, 0,

            0,-1.279f,1,
            0,-1.279f,-0.5f,
            3.242f,-3.035f, 0,

            0,-1.279f,1,
            3.242f,-3.035f, 0.5f,
            3.242f,-3.035f, 0,

            3.242f,-3.035f, 0.5f,
            3.242f,-3.035f, 0,
            1.832f,-0.252f,-0.5f,

            3.242f,-3.035f, 0.5f,
            1.832f,-0.252f,1,
            1.832f,-0.252f,-0.5f,

            //
            -1.118f,1.122f,1,
            0,-1.279f,1,
            -1.809f,-0.301f,1,

            -1.118f,1.122f,-0.5f,
            0,-1.279f,-0.5f,
            -1.809f,-0.301f,-0.5f,

            -1.118f,1.122f,1,
            0,-1.279f,1,
            1.143f,1.123f, 1,

            -1.118f,1.122f,-0.5f,
            0,-1.279f,-0.5f,
            1.143f,1.123f, -0.5f,

            0,-1.279f,1,
            1.143f,1.123f, 1,
            1.832f,-0.252f,1,

            0,-1.279f,-0.5f,
            1.143f,1.123f, -0.5f,
            1.832f,-0.252f,-0.5f,
            //
            -1.118f,1.122f,1,
            0,3.4f,0.5f,
            1.143f,1.123f, 1,

            -1.118f,1.122f,-0.5f,
            0,3.4f,0,
            1.143f,1.123f, -0.5f,

            -1.118f,1.122f,1,
            -1.118f,1.122f,-0.5f,
            0,3.4f,0,

            -1.118f,1.122f,1,
            0,3.4f,0,
            0,3.4f,0.5f,

            0,3.4f,0.5f,
            0,3.4f,0,
            1.143f,1.123f, -0.5f,

            0,3.4f,0.5f,
            1.143f,1.123f, -0.5f,
            1.143f,1.123f, 1,
            //
            1.143f,1.123f,1,
            4.28f,1.123f,0.5f,
            1.823f,-0.252f,1,

            1.143f,1.123f,-0.5f,
            4.28f,1.123f,0,
            1.823f,-0.252f,-0.5f,

            1.143f,1.123f,1,
            1.143f,1.123f,-0.5f,
            4.28f,1.123f,0,

            1.143f,1.123f,1,
            4.28f,1.123f,0,
            4.28f,1.123f,0.5f,

            1.823f,-0.252f,1,
            1.823f,-0.252f,-0.5f,
            4.28f,1.123f,0,

            1.823f,-0.252f,1,
            4.28f,1.123f,0,
            4.28f,1.123f,0.5f

    };


    float colorcyan[] = myColor.cyan();
    float colorblue[] = myColor.blue();
    float colorred[] = myColor.red();
    float colorgray[] = myColor.gray();
    float colorgreen[] = myColor.green();
    float coloryellow[] = myColor.yellow();

    float colorlightgray[] = myColor.lightgray();
    float colorgold[] = myColor.gold();
    float colorlightgold[] = myColor.lightgold();
    float colordarkgold[] = myColor.Darkgold();
    float colorlightyellow[] = myColor.lightyellow();
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
    public Star() {
        for(int i = 0 ; i< mVerticesData.length; i++){
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
        GLES30.glUniform4fv(mColorHandle, 1, colorgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //2
        GLES30.glUniform4fv(mColorHandle, 1, colorgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
//      //3
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //4
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //5
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //6
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //7
        GLES30.glUniform4fv(mColorHandle, 1, colordarkgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1,  colordarkgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1,  colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1,  colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;


        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightyellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgold, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //7
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;
        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //8
        GLES30.glUniform4fv(mColorHandle, 1, colorlightgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

    }
}
