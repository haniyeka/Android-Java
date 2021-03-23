package com.example.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TwoplayersActivity extends AppCompatActivity {

    private BroadView_TwoPlayer broadView_twoPlayer;
    private TictactoeTwoPlayer tictactoeTwoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoplayers);
        broadView_twoPlayer = findViewById(R.id.board);
        tictactoeTwoPlayer = new TictactoeTwoPlayer();
        broadView_twoPlayer.setGame(tictactoeTwoPlayer);
        broadView_twoPlayer.setActivity(this);
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
        String msg = (p == 'T') ? "\nTie" : "\nPlayer " + p + " won the game";

        new AlertDialog.Builder(this).setTitle("Game is over").
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
        broadView_twoPlayer.invalidate();
    }

}
