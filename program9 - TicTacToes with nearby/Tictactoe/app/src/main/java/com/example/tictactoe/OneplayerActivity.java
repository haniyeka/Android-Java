package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OneplayerActivity extends AppCompatActivity {

    private BroadView_OnePlayer broadView_onePlayer;
    private TictactoeOnePlayer tictactoeOnePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneplayer);
        char player = getIntent().getExtras().getChar("player");
        broadView_onePlayer = findViewById(R.id.board);
        tictactoeOnePlayer = new TictactoeOnePlayer(player);
        tictactoeOnePlayer.define_Player(player);
        broadView_onePlayer.setGame(tictactoeOnePlayer);
        broadView_onePlayer.setActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void is_Game_Over(char p) {
        String message = (p == 'T') ? "\nTie" : "\nPlayer " + p + " won";
        new AlertDialog.Builder(this).setTitle("Game is over.").
                setMessage(message).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        tictactoeOnePlayer.start_game();
        broadView_onePlayer.invalidate();
    }
}
