//Haniye Kashgarani
package com.example.battlebot_haniye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.button);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectserver();
            }
        });
    }

    private void connectserver() {
        EditText add = findViewById(R.id.IP);
        String IP = add.getText().toString();
        Intent intent=new Intent(this,ServerActivity.class);
        intent.putExtra("IPaddress", IP);
        startActivity(intent);
    }


}
