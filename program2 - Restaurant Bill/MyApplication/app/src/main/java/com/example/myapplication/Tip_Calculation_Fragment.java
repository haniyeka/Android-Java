package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class Tip_Calculation_Fragment extends Fragment implements RadioGroup.OnCheckedChangeListener, Switch.OnCheckedChangeListener {


    Switch Split_Switch;
    EditText Bill_EditText ;
    Float Bill_amount ;
    RadioGroup Rounding_Radio;
    EditText Tip_Percent_EditText;
    Float Tip_Percent_amount;
    EditText split_EditText;
    int Split = 1;
    int RoundingOption = 0;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_tip_calculation, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Rounding_Radio = view.findViewById(R.id.radio);
        Rounding_Radio.setOnCheckedChangeListener(this);
        Bill_EditText = view.findViewById(R.id.Bill_EditText);
        Tip_Percent_EditText = view.findViewById(R.id.Tip_Percentage_EditText);
        Split_Switch = view.findViewById(R.id.Split_Switch);
        split_EditText = view.findViewById(R.id.Split_EditText);
        split_EditText.setEnabled(false);
        Split_Switch.setOnCheckedChangeListener(this);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Bill_EditText.getText().toString().equalsIgnoreCase("") && !Bill_EditText.getText().toString().equalsIgnoreCase("")){
                    Bill_amount = Float.valueOf(Bill_EditText.getText().toString());
                    Tip_Percent_amount = Float.valueOf(Tip_Percent_EditText.getText().toString());
                    if(split_EditText.getText().toString().equalsIgnoreCase(""))
                        Split = 1;
                    else
                        Split = Integer.valueOf(split_EditText.getText().toString());
                    String Message = "";
                    double Tip,Total_Bill,Splits = 0;
                    if(RoundingOption == 1){
                        Tip = Math.ceil((Bill_amount * Tip_Percent_amount)/100);
                        Total_Bill = Bill_amount + Tip;
                        Splits = Total_Bill/Split;
                    }
                    else if(RoundingOption == 2){
                        Tip = (Bill_amount * Tip_Percent_amount)/100;
                        Total_Bill =  Math.ceil(Bill_amount + Tip);
                        Splits = Total_Bill/Split;
                    }
                    else {
                        Tip = (Bill_amount * Tip_Percent_amount)/100;
                        Total_Bill = Bill_amount + Tip;
                        Splits = Total_Bill/Split;
                    }
                    if(Split>1)
                        Message = "Total Bill: "+Total_Bill+"\nRelated Tip: "+Tip + "\nFor " + Split + " Split:" + Splits;
                    else
                        Message = "Total Bill: "+Total_Bill+"\nRelated Tip: "+Tip;
                    new AlertDialog.Builder(getContext())
                            .setTitle("Info")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Bill_EditText.setText("");
                                    Tip_Percent_EditText.setText("");
                                    split_EditText.setText("");
                                }
                            })
                            .setCancelable(true)
                            .setMessage(Message)
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .show();}
                else
                    Snackbar.make(getView(),"Please enter bill amount and tip percentage!",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (radioGroup ==Rounding_Radio) { //if not, we are in trouble!
            if (checkedId == R.id.radio_tip) {
                // information radio button clicked
                RoundingOption = 1;
            } else if (checkedId == R.id.radio_total_bill) {
                // Confirmation radio button clicked
                RoundingOption = 2;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        split_EditText.setEnabled(b);
    }

}
