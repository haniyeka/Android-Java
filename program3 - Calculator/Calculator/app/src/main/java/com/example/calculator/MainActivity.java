package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //numbers storage
    private float Mem; String Number = ""; String AdditionalString = ""; float f_number; float s_number;
    //actions
    private char ACTION; private final char ADDITION = '+'; private final char SUBTRACTION = '-'; private final char MULTIPLICATION = '*'; private final char DIVISION = '/'; private final char EQU = 0;
    //numbers
    private Button zero; private Button one; private Button two; private Button three; private Button four; private Button five; private Button six; private Button seven; private Button eight; private Button nine;
    //computing buttons
    private Button AddButton; private Button SubtractionButton; private Button MultiplicationButton; private Button DivisionButton; private Button EqualButton;
    //extra buttons
    private Button ClearButton; private Button MemoryButton; private Button MemoryRecallButton; private Button DeleteButton; private Button PointButton; private Button SignButton;
    //displays
    private TextView AdditionalInfo; private TextView DisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInitialization();
        SetOnClickListenersNumbers();
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Number != "") {
                    equalFunc();
                    f_number = Float.valueOf(Number);
                    AdditionalString = Number + " + ";
                    AdditionalInfo.setText(AdditionalString);
                    AdditionalInfo.setText(AdditionalString);
                    DoMath();
                    ACTION = ADDITION;
                    Number = "";
                }
            }
        });
        SubtractionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Number != "") {
                    equalFunc();
                    f_number = Float.valueOf(Number);
                    AdditionalString = Number + " - ";
                    AdditionalInfo.setText(AdditionalString);
                    AdditionalInfo.setText(AdditionalString);
                    DoMath();
                    ACTION = SUBTRACTION;
                    Number = "";
                }
            }
        });

        MultiplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Number != "") {
                    equalFunc();
                    f_number = Float.valueOf(Number);
                    AdditionalString = Number + " * ";
                    AdditionalInfo.setText(AdditionalString);
                    AdditionalInfo.setText(AdditionalString);
                    DoMath();
                    ACTION = MULTIPLICATION;
                    Number = "";
                }
            }
        });

        DivisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if(Number != ""){
                equalFunc();
                f_number = Float.valueOf(Number);
                AdditionalString = Number + " / ";
                AdditionalInfo.setText(AdditionalString);
                DoMath();
                ACTION = DIVISION;
                Number = "";
            }
            }
        });
        EqualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f_number == Float.NaN){
                    f_number = Float.valueOf(Number);
                }
                equalFunc();
                if(AdditionalInfo.getText().toString().trim() == ""){
                    AdditionalInfo.setText("");
                }
                else {
                    if(!AdditionalInfo.getText().toString().trim().contains("=")) {
                        AdditionalInfo.setText(AdditionalInfo.getText().toString() + String.valueOf(s_number) + "=");
                    }
                    else if(!AdditionalInfo.getText().toString().trim().contains("/") && !AdditionalInfo.getText().toString().trim().contains("*") && !AdditionalInfo.getText().toString().trim().contains("-") && !AdditionalInfo.getText().toString().trim().contains("+")){
                        AdditionalInfo.setText(String.valueOf(f_number));
                    }
                }
            }
        });

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });
        MemoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalFunc();
                if(DisplayTextView.getText().toString()!=""){
                    Mem = Float.valueOf(DisplayTextView.getText().toString());
                    Number = "";
                    f_number = Float.NaN;
                    DisplayTextView.setText(Number);
                }
            }
        });
        MemoryRecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = String.valueOf(Mem);
                DisplayTextView.setText(Number);
            }
        });
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Number.length()>=1 ) {
                    Number = Number.substring(0, Number.length() - 1);
                    DisplayTextView.setText(Number);
                }
                else{
                    Number ="";
                    DisplayTextView.setText("");
                }
            }
        });

        PointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Number.contains(".")){
                    Number = Number + ".";
                    DisplayTextView.setText(Number);
                }
            }
        });
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Number.contains("+") && !Number.contains("-")){
                    Number = "-"+ Number;
                }
                else if(Number.contains("+")){
                    Number = Number.replace("+","-");
                }
                else if(Number.contains("-")){
                    Number = Number.replace("-","+");
                }
                else if(Number ==""){
                    Number = "-";
                }
                DisplayTextView.setText(Number);
            }
        });

    }
    private void DoMath(){
        if(!Float.isNaN(f_number)){
            s_number = Float.valueOf(Number);
            switch(ACTION){
                case ADDITION:
                    f_number = s_number + f_number;
                    Number = String.valueOf(f_number);
                    ACTION = Character.MIN_VALUE;
                    break;
                case SUBTRACTION:
                    f_number = f_number - s_number;
                    Number = String.valueOf(f_number);
                    ACTION = Character.MIN_VALUE;
                    break;
                case MULTIPLICATION:
                    f_number = s_number * f_number;
                    Number = String.valueOf(f_number);
                    ACTION = Character.MIN_VALUE;
                    break;
                case DIVISION:
                    f_number = f_number / s_number;
                    Number = String.valueOf(f_number);
                    ACTION = Character.MIN_VALUE;
                    break;
                case EQU:
                    ACTION = Character.MIN_VALUE;
                    break;
            }
        }
        else{
            f_number = Float.valueOf(DisplayTextView.getText().toString());
        }
    }

    void equalFunc(){
        DoMath();
        ACTION = EQU;
        DisplayTextView.setText(String.valueOf(f_number));
    }
    void ViewInitialization(){
        DisplayTextView = (TextView)findViewById(R.id.display);
        AddButton = (Button)findViewById(R.id.add);
        SubtractionButton = (Button)findViewById(R.id.sub);
        MultiplicationButton = (Button)findViewById(R.id.mult);
        DivisionButton = (Button)findViewById(R.id.div);
        EqualButton = (Button)findViewById(R.id.eq);
        SignButton =(Button) findViewById(R.id.negpos);
        PointButton =(Button) findViewById(R.id.dot);
        AdditionalInfo =(TextView)findViewById(R.id.txt2);
        one = (Button) findViewById(R.id.n1);
        zero = (Button)findViewById(R.id.n0);
        two = (Button)findViewById(R.id.n2);
        three = (Button)findViewById(R.id.n3);
        four = (Button)findViewById(R.id.n4);
        five = (Button)findViewById(R.id.n5);
        six = (Button)findViewById(R.id.n6);
        seven = (Button)findViewById(R.id.n7);
        eight = (Button)findViewById(R.id.n8);
        nine = (Button)findViewById(R.id.n9);
        MemoryRecallButton =(Button) findViewById(R.id.MR);
        DeleteButton =(Button) findViewById(R.id.DEL);
        ClearButton = (Button) findViewById(R.id.cl);
        MemoryButton =(Button) findViewById(R.id.M);
    }
    void EnterNumber(int digit){
        Number = Number + String.valueOf(digit);
        DisplayTextView.setText(Number);
    }
    void SetOnClickListenersNumbers(){
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Number.trim()!= ""){
                    EnterNumber(0);
                }
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(1);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(2);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(3);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(4);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(5);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(6);
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(7);
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(8);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterNumber(9);
            }
        });
    }
    void Clear(){
        Mem = 0;
        Number = "";
        f_number = Float.NaN ;
        s_number = Float.NaN;
        Mem = Float.NaN;
        DisplayTextView.setText("");
        AdditionalInfo.setText("");
    }

}