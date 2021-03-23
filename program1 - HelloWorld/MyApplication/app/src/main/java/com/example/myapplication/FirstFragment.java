package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    EditText personName ;
    String PersonName ;
    private OnFragmentInteractionListener mListener;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personName = view.findViewById(R.id.TextBoxForName);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonName = personName.getText().toString();
                Snackbar snackbar = Snackbar.make(view ,"Hello "+PersonName+"! Welcome to Google Developer's world", Snackbar.LENGTH_LONG);
                snackbar.show();
                mListener = (OnFragmentInteractionListener) getActivity();
                mListener.onFragmentInteraction(PersonName);
                Log.e("LOG", "onFragmentInteraction - From Fragment: " + "Hello "+PersonName+"! Welcome to Google Developer's world");
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String username);
    }
}
