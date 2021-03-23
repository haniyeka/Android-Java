package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoosePlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
    }

    public void ChooseX(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, OneplayerActivity.class);
        intent.putExtra("player",'X');
        startActivity(intent);
    }

    public void ChooseY(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, OneplayerActivity.class);
        intent.putExtra("player",'O');
        startActivity(intent);
    }
}
