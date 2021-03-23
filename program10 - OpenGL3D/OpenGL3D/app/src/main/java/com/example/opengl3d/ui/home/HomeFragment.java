package com.example.opengl3d.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.opengl3d.R;
import com.example.opengl3d.myGlSurfaceViewHeart;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Integer> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = new myGlSurfaceViewHeart(getContext());
        return root;
    }
}
