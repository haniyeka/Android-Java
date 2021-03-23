package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

public class NearbyConnectionActivity extends AppCompatActivity implements X_ODialog.ChooseXOListener,Serializable{
    String TAG = "Find Player";
    String UserNickname = "";
    public static final int REQUEST_ACCESS_COURSE_LOCATION= 1;
    boolean IsAdvertising = false;
    boolean IsDiscovering = false;
    BluetoothAdapter BluetoothAdapter = null;
    private static final int REQUEST_ENABLE_BT = 2;
    LinearLayout linearLayout;
    TextView connectionLogger;
    TictactoeTwoPlayerNearby tictactoeTwoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_connection);
        linearLayout = findViewById(R.id.buttonLayout);
        connectionLogger = findViewById(R.id.connectionLogger);
        tictactoeTwoPlayer = new TictactoeTwoPlayerNearby(this, connectionLogger, this,getSupportFragmentManager());
        tictactoeTwoPlayer.linearLayout = linearLayout;
        checkpermission();
        startbt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_nearby, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_connection) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void FindFriendButton(View view) {
        UserNickname = "Client";
        tictactoeTwoPlayer.Usernickname = UserNickname;
        if(IsAdvertising){
            tictactoeTwoPlayer.stopSearching();
            connectionLogger.setText("");
            IsAdvertising = false;
        }
        else{
            tictactoeTwoPlayer.startSearching();
            connectionLogger.setText("Searching for your friend. \nWait...");
            IsAdvertising = true;
        }
    }

    public void LetFindYouButton(View view) {
        UserNickname = "Server";
        tictactoeTwoPlayer.Usernickname = UserNickname;
        if (IsDiscovering) {
            tictactoeTwoPlayer.stopBeingFind();//in discovery mode, turn it off
            connectionLogger.setText("");
            IsDiscovering = false;
        }
        else{
            tictactoeTwoPlayer.startBeingFind();
            tictactoeTwoPlayer.should_I_addButton = true;
            IsDiscovering = true;
            connectionLogger.setText("Searching for a your friend! \nPlease wait...");
        }
    }

    public void startbt() {
        BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (BluetoothAdapter == null) {
            // Device does not support Bluetooth
            return;
        }
        //make sure bluetooth is enabled.
        if (!BluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            //bluetooth is on, so list paired devices from here.
        }
    }

    void checkpermission() {
        //first check to see if I have permissions (marshmallow) if I don't then ask, otherwise start up the demo.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_COURSE_LOCATION);
        } else {
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] players, int position) {
        tictactoeTwoPlayer.Player = players[position];
        tictactoeTwoPlayer.send(players[position]);
        linearLayout.removeView(tictactoeTwoPlayer.pickPlayer);
        connectionLogger.setText("Wait for your friend to accept...");
    }

    @Override
    public void onNegativeButtonClicked() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
