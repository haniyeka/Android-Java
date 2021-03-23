package com.example.tictactoe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

//Programmer: Haniye Kashgarani
//Tictactoe with nearby
//couse# : 5735
//Program #3

public class MainActivity extends AppCompatActivity {
    String TAG = "Main Activity";
    public static final int REQUEST_ACCESS_COURSE_LOCATION= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Oneplayer(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChoosePlayer.class);
        startActivity(intent);
    }

    public void TwoPlayer(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, TwoplayersActivity.class);
        startActivity(intent);
    }

}
