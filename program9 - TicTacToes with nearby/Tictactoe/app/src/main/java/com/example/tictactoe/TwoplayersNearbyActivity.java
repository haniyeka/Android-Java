package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TwoplayersNearbyActivity extends AppCompatActivity {

    public BroadView_TwoPlayerNearby broadViewTwoPlayer;
    public static TictactoeTwoPlayerNearby tictactoeTwoPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoplayers_nearby);
        broadViewTwoPlayer = findViewById(R.id.board);
        broadViewTwoPlayer.setGame(tictactoeTwoPlayer);
        broadViewTwoPlayer.setActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            startGame();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void isGameOver(char p) {
        String msg = (p == 'T') ? "\nTie" : "\nPlayer " + p + " won the game!";
        if(p == 'T'){
            tictactoeTwoPlayer.send("tie");
        }
        else {
            tictactoeTwoPlayer.send("winner");
        }
        new AlertDialog.Builder(this).setTitle("Game is Over").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        startGame();
                    }
                }).show();
    }

    private void startGame() {
        tictactoeTwoPlayer.startGame();
        broadViewTwoPlayer.invalidate();
    }

}
