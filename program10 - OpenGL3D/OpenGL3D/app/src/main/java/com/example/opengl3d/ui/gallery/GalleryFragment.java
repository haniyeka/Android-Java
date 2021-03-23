package com.example.opengl3d.ui.gallery;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.opengl3d.R;
import com.example.opengl3d.myGlSurfaceViewTwisty;
import com.example.opengl3d.myRendererTwistyStructure;

public class GalleryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = new View(getContext());
        if (detectOpenGLES30()) {
            view = new myGlSurfaceViewTwisty(getContext());
            //so we know it a opengl 3.0 and use our extended GLsurfaceview.
        } else {
            Log.e("openglcube", "OpenGL ES 3.0 not supported on device.  Exiting...");
        }
        return view;
    }


    public boolean detectOpenGLES30() {
        ActivityManager am =
                (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }
}
