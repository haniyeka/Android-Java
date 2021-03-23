package com.example.opengl3d;

/**
 * Created by Seker on 7/1/2015.
 *
 * Some color static methods so I can setup the color quickly and not think hard either.
 */
import android.graphics.Color;

public class myColor {
    static float[] red() {
        return new float[]{
                Color.red(Color.RED) / 255f,
                Color.green(Color.RED) / 255f,
                Color.blue(Color.RED) / 255f,
                1.0f
        };
    }

    static float[] green() {
        return new float[]{
                Color.red(Color.GREEN) / 255f,
                Color.green(Color.GREEN) / 255f,
                Color.blue(Color.GREEN) / 255f,
                1.0f
        };
    }

    static float[] blue() {
        return new float[]{
                Color.red(Color.BLUE) / 255f,
                Color.green(Color.BLUE) / 255f,
                Color.blue(Color.BLUE) / 255f,
                1.0f
        };
    }

    static float[] yellow() {
        return new float[]{
                Color.red(Color.YELLOW) / 255f,
                Color.green(Color.YELLOW) / 255f,
                Color.blue(Color.YELLOW) / 255f,
                1.0f
        };
    }

    static float[] cyan() {
        return new float[]{
                Color.red(Color.CYAN) / 255f,
                Color.green(Color.CYAN) / 255f,
                Color.blue(Color.CYAN) / 255f,
                1.0f
        };
    }

    static float[] gray() {
        return new float[]{
                Color.red(Color.GRAY) / 255f,
                Color.green(Color.GRAY) / 255f,
                Color.blue(Color.GRAY) / 255f,
                1.0f
        };
    }


    static float[] pink() {
        return new float[]{
                254f / 255f,
                211f / 255f,
                202f / 255f,
                1.0f
        };
    }


    static float[] pinkDark() {
        return new float[]{
                252f / 255f,
                137f / 255f,
                112f / 255f,
                1.0f
        };
    }


    static float[] lightgray() {
        return new float[]{
                228f / 255f,
                228f / 255f,
                228f / 255f,
                1.0f
        };
    }

    static float[] lightgold() {
        return new float[]{
                255f / 255f,
                255f / 255f,
                82f / 255f,
                1.0f
        };
    }

    static float[] gold() {
        return new float[]{
                238f / 255f,
                238f / 255f,
                99f / 255f,
                1.0f
        };
    }


    static float[] Darkgold() {
        return new float[]{
                255f / 255f,
                215f / 255f,
                0f / 255f,
                1.0f
        };
    }


    static float[] lightyellow() {
        return new float[]{
                255f / 255f,
                248f / 255f,
                221f / 255f,
                1.0f
        };
    }




}
