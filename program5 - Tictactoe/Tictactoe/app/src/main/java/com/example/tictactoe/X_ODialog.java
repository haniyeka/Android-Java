package com.example.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class X_ODialog extends DialogFragment {
    String selection = "";
    int position = 0;

    public interface ChooseXOListener{
        void onPositiveButtonClicked(String[] players, int position);
        void onNegativeButtonClicked();
    }
    ChooseXOListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener= (ChooseXOListener) context;
        } catch (Exception e){
            throw new ClassCastException(getActivity().toString() + " listener must implemented");
        }
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final String[] players = getActivity().getResources().getStringArray(R.array.players);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("We found a playmate! \nPick your player sign.");
        builder.setSingleChoiceItems(R.array.players, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = players[which];
                position = which;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onPositiveButtonClicked(players,position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Canceled! Start over!", Toast.LENGTH_SHORT);
                mListener.onNegativeButtonClicked();
            }
        });
        return builder.create();
    }
}
