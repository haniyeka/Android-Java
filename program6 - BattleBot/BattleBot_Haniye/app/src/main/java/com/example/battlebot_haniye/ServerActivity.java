package com.example.battlebot_haniye;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ServerActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int SERVERPORT = 3012;
    private ClientThread clientThread;
    private Thread thread;
    private LinearLayout msgList;
    private Handler handler;
    private int clientTextColor;
    private EditText edMessage;
    private String SERVER_IP;
    JoystickView joystickMove = null;
    JoystickView joystickShot = null;
    boolean started = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        setTitle("Client");
        clientTextColor = ContextCompat.getColor(this, R.color.green);
        handler = new Handler();
        msgList = findViewById(R.id.msgList);
        edMessage = findViewById(R.id.edMessage);
        Intent intent = getIntent();
        SERVER_IP = intent.getExtras().getString("IPaddress");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final int[] counter = {0,0};
        joystickMove = (JoystickView) findViewById(R.id.joystickView);
        joystickMove.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                // do whatever you want
                if(!started){
                    Toast.makeText(getApplicationContext(),"Press 'CONNECT' then 'SET BOT' to play",Toast.LENGTH_SHORT).show();
                }
                if(counter[0] == 15)
                {
                    counter[0] =0;
                }
                if(counter[0]==0){
                    Log.v("angleMove: ", String.valueOf(angle));
                    Log.v("counter: ", String.valueOf(counter[0]));
                    if(angle <= 22.5 || angle > 337.5){
                        //right
                        String clientMessage = "move 1 0";
                        for(int i=0;i<8;i++){
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if(angle > 22.5 && angle <= 67.5) {
                        //right up
                        for (int i = 0; i < 8; i++) {
                            String clientMessage = "move 2 -1";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if( angle > 67.5 && angle <= 112.5){
                        //up
                        for(int i=0;i<8;i++){
                            String clientMessage = "move 0 -4";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if( angle > 112.5 && angle <= 157.5){
                        //left up
                        for(int i=0;i<8;i++){
                            String clientMessage = "move -2 -1";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if( angle > 157.5 && angle <= 202.5){
                        //left

                        for(int i=0;i<8;i++){
                            String clientMessage = "move -1 0";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if(angle > 202.5 && angle <= 247.5){
                        //left down
                        for(int i=0;i<8;i++){
                            String clientMessage = "move -1 1";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }

                    }
                    else if(angle > 247.5 && angle <= 292.5){
                        //down
                        String clientMessage = "move 0 4";
                        showMessage(clientMessage, Color.BLUE);
                        for(int i=0;i<8;i++){
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                    else if(angle > 292.5  && angle <= 337.5){
                        //right down
                        for(int i=0;i<8;i++){
                            String clientMessage = "move 1 1";
                            clientThread.sendMessage(clientMessage);
                            clientThread.sendMessage("noop");
                        }

                        String clientMessage2 = "scan";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                    }
                }
                counter[0]++;

            }
        });

        joystickShot = (JoystickView) findViewById(R.id.joystickShot);
        joystickShot.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                // do whatever you want
                if(!started){
                    Toast.makeText(getApplicationContext(),"Press 'CONNECT' then 'SET BOT' to play",Toast.LENGTH_SHORT).show();
                }
                if(counter[1] == 15)
                {
                    counter[1] =0;
                }
                if(counter[1] == 0){

                    Log.v("angleShot: ", String.valueOf(angle));
                    Log.v("counter: ", String.valueOf(counter[1]));

                    if(angle <= 22.5 || angle > 337.5){
                        //right
                        String clientMessage2 = "fire 90";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                    else if(angle > 22.5 && angle <= 67.5) {
                        //right up.
                        String clientMessage2 = "fire 45";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                    else if( angle > 67.5 && angle <= 112.5){
                        //up

                        String clientMessage2 = "fire 0";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                    else if( angle > 112.5 && angle <= 157.5){
                        //left up

                        String clientMessage2 = "fire -45";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                    else if( angle > 157.5 && angle <= 202.5){
                        //left

                        String clientMessage2 = "fire -90";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }

                    }
                    else if(angle > 202.5 && angle <= 247.5){
                        //left down

                        String clientMessage2 = "fire -135";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }


                    }
                    else if(angle > 247.5 && angle <= 292.5){
                        //down

                        String clientMessage2 = "fire 180";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                    else if(angle > 292.5  && angle <= 337.5){
                        //right down

                        String clientMessage2 = "fire 135";
                        showMessage(clientMessage2, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage2);
                        }
                        for(int i=0;i<9;i++){
                            String clientMessage = "noop";
                            showMessage(clientMessage, Color.BLUE);
                            if (null != clientThread) {
                                clientThread.sendMessage(clientMessage);
                            }
                        }

                        String clientMessage = "scan";
                        showMessage(clientMessage, Color.BLUE);
                        if (null != clientThread) {
                            clientThread.sendMessage(clientMessage);
                        }
                    }
                }
                counter[1]++;
            }
        });
    }

    public TextView textView(String message, int color) {
        if (null == message || message.trim().isEmpty()) {
            message = "<Empty Message>";
        }
        TextView tv = new TextView(this);
        tv.setTextColor(color);
        tv.setText(message + " [" + getTime() + "]");
        tv.setTextSize(20);
        tv.setPadding(0, 5, 0, 0);
        return tv;
    }

    public void showMessage(final String message, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                msgList.addView(textView(message, color));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.connect_server) {
            msgList.removeAllViews();
            showMessage("Connecting to Server...", clientTextColor);
            clientThread = new ClientThread();
            thread = new Thread(clientThread);
            thread.start();
            showMessage("Connected to Server...", clientTextColor);
            return;
        }
        if (view.getId() == R.id.setBot){
            started = true;
            String clientMessage = "Haniye 1 0 1";
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }

            clientMessage = "scan";
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
        }
        if (view.getId() == R.id.noop1){
            String clientMessage = "noop";
            showMessage(clientMessage, Color.BLUE);
            for(int i=0;i<10;i++){
                if (null != clientThread) {
                    clientThread.sendMessage(clientMessage);
                }
            }

            clientMessage = "scan";
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
        }
        if (view.getId() == R.id.send_data) {
            String clientMessage = edMessage.getText().toString().trim();
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }

            clientMessage = "scan";
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
        }
    }

    class ClientThread implements Runnable {

        private Socket socket;
        private BufferedReader input;

        @Override
        public void run() {
            try {
                SERVER_IP=SERVER_IP;
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);

                while (!Thread.currentThread().isInterrupted()) {

                    this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = input.readLine();
                    if (null == message || "Disconnect".contentEquals(message)) {
                        Thread.interrupted();
                        message = "Server Disconnected.";
                        showMessage(message, Color.RED);
                        break;
                    }
                    showMessage("Server: " + message, clientTextColor);
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        void sendMessage(final String message) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (null != socket) {
                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);
                            out.println(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != clientThread) {
            clientThread.sendMessage("Disconnect");
            clientThread = null;
        }
    }

}
