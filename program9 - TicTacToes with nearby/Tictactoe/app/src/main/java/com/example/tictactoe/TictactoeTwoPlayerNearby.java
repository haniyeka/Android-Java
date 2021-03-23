package com.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class TictactoeTwoPlayerNearby implements java.io.Serializable{

    private char[] boardArray;
    private boolean isgameOver;
    public char currentPlayer;
    public String TAG="TicTacToe";
    boolean mIsAdvertising = false;
    boolean mIsDiscovering = false;
    public Context currentContext;
    FragmentManager fragmentManager;
    boolean IsConnected = false;
    String ConnectedEndPointId;
    public  String Usernickname;
    public String Opponent;
    public String Player;
    public Activity currentActivity;
    public TwoplayersNearbyActivity gActivity;
    public static final String ServiceId = "edu.cs4730.nearbyconnectiondemo";  //need a unique value to identify app.
    public static final Strategy STRATEGY = Strategy.P2P_STAR;
    Boolean should_I_addButton = false;
    LinearLayout linearLayout;
    TextView connectionLogger;
    Button pickPlayer;
    Boolean isNewGame = true;

    public TictactoeTwoPlayerNearby(Context context, TextView connectionresult, Activity activity, FragmentManager fm) {
        currentActivity = activity;
        fragmentManager = fm;
        connectionLogger = connectionresult;
        currentContext = context;
        boardArray = new char[9];
        startGame();
    }

    public boolean isGameOver() {
        return isgameOver;
    }

    public void switchPlayer() {
        if(currentPlayer == 'X')
        {
            currentPlayer = 'O';
        }
        else{
            currentPlayer = 'X';
        }
    }

    public void play_otherSide(int location){
        char win = play(location);
        gActivity.broadViewTwoPlayer.invalidate();
        if (win != ' ') {
            gActivity.isGameOver(win);
            //send("winner");
        }
        send("agree");
    }

    public char play(int x, int y) {
        if (!isgameOver && boardArray[3 * y + x] == ' ') {
            boardArray[3 * y + x] = currentPlayer;
            send(String.valueOf(3*y+x));
            switchPlayer();
        }
        return check_isGameOver();
    }

    public char play(int A) {
        if (!isgameOver && boardArray[A] == ' ') {
            boardArray[A] = currentPlayer;
            send(String.valueOf(A));
            switchPlayer();
        }
        return check_isGameOver();
    }

    public char getLocation(int x, int y) {
        return boardArray[3 * y + x];
    }

    public char check_isGameOver() {
        for (int i = 0; i < 3; i++) {
            if (getLocation(i, 0) != ' ' &&
                    getLocation(i, 0) == getLocation(i, 1) &&
                    getLocation(i, 1) == getLocation(i, 2)) {
                isgameOver = true;
                return getLocation(i, 0);
            }
            if (getLocation(0, i) != ' ' &&
                    getLocation(0, i) == getLocation(1, i) &&
                    getLocation(1, i) == getLocation(2, i)) {
                isgameOver = true;
                return getLocation(0, i);
            }
        }
        if (getLocation(0, 0) != ' ' &&
                getLocation(0, 0) == getLocation(1, 1) &&
                getLocation(1, 1) == getLocation(2, 2)) {
            isgameOver = true;
            return getLocation(0, 0);
        }
        if (getLocation(2, 0) != ' ' &&
                getLocation(2, 0) == getLocation(1, 1) &&
                getLocation(1, 1) == getLocation(0, 2)) {
            isgameOver = true;
            return getLocation(2, 0);
        }
        for (int i = 0; i < 9; i++) {
            if (boardArray[i] == ' ') {
                return ' ';
            }
        }
        return 'T';
    }

    public void startSearching() {
        Nearby.getConnectionsClient(currentContext)
                .startAdvertising(
                        Usernickname, //human readable name for the endpoint.
                        ServiceId,  //unique identifier for advertise endpoints
                        connectionLifecycleCallback,  //callback notified when remote endpoints request a connection to this endpoint.
                        new AdvertisingOptions.Builder().setStrategy(STRATEGY).build()
                )
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                mIsAdvertising = true;
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
    }

    protected void startBeingFind() {
        Nearby.getConnectionsClient(currentContext)
                .startDiscovery(
                        ServiceId,   //id for the service to be discovered.  ie, what are we looking for.
                        new EndpointDiscoveryCallback() {  //callback when we discovery that endpoint.
                            @Override
                            public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                                IsConnected = true;
                                createConnection(endpointId);
                            }

                            @Override
                            public void onEndpointLost(String endpointId) {
                                IsConnected = false;
                            }
                        },
                        new DiscoveryOptions.Builder().setStrategy(STRATEGY).build()
                )  //options for discovery.
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

    }

    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, final ConnectionInfo connectionInfo) {
                    final String endPointName = connectionInfo.getEndpointName();
                    if(Usernickname == "Client"){
                        linearLayout.removeView(currentActivity.findViewById(R.id.LetFindingButton));
                        linearLayout.removeView(currentActivity.findViewById(R.id.FindFriendButton));
                        linearLayout.removeView(currentActivity.findViewById(R.id.text));
                        connectionLogger.setText("We found a playmate! \nWait for your friend to pick a player!");
                    }
                    Nearby.getConnectionsClient(currentActivity).acceptConnection(endpointId, //mPayloadCallback);
                            new PayloadCallback() {
                                @Override
                                public void onPayloadReceived(String endpointId, Payload payload) {
                                    IsConnected = true;
                                    if (payload.getType() == Payload.Type.BYTES) {
                                        String stuff = new String(payload.asBytes());
                                        Toast.makeText(currentContext,stuff,Toast.LENGTH_SHORT).show();
                                        if (stuff.contains("Player X") || stuff.contains("Player O")){
                                            Opponent = stuff;
                                            if(stuff.contains("Player X")) {
                                                Player = "Player O";
                                            }
                                            else if(stuff.contains("Player O"))
                                            {
                                                Player = "Player X";
                                            }
                                            connectionLogger.setText("Your friend want to player with "+ Opponent +"!\nYour symbol will be "+ Player+ "\nDo you accept?");
                                            Button Letsplay = new Button(currentContext);
                                            Letsplay.setText("Let's Play!");
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 183);
                                            lp.topMargin = 25;
                                            lp.gravity = Gravity.CENTER;
                                            try {
                                                linearLayout.removeView(Letsplay);
                                            }catch (Exception e){}
                                            linearLayout.addView(Letsplay, lp);
                                            Letsplay.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    send("agree");
                                                    isNewGame = true;
                                                    TwoplayersNearbyActivity.tictactoeTwoPlayer = TictactoeTwoPlayerNearby.this;
                                                    Intent intent = new Intent(currentContext, TwoplayersNearbyActivity.class);
                                                    currentActivity.startActivity(intent);
                                                }
                                            });
                                        }
                                        else if (stuff.startsWith("agree")){
                                            if(isNewGame == true) {
                                                TwoplayersNearbyActivity.tictactoeTwoPlayer = TictactoeTwoPlayerNearby.this;
                                                isNewGame = false;
                                                Intent intent = new Intent(currentContext, TwoplayersNearbyActivity.class);
                                                currentActivity.startActivity(intent);
                                            }
                                            else{

                                            }
                                        }
                                        else if(stuff.contains("disagree")){
                                            connectionLogger.setText("The player wants to play with this player! ");
                                        }
                                        else if(stuff.startsWith("0") || stuff.startsWith("1") || stuff.startsWith("2") || stuff.startsWith("3") || stuff.startsWith("4") || stuff.startsWith("5") || stuff.startsWith("6") || stuff.startsWith("7") || stuff.startsWith("8")) {
                                            play_otherSide(Integer.valueOf(stuff));

                                        }
                                        else if(stuff.startsWith("nowinner") || stuff.startsWith("winner") || stuff.startsWith("tie")){
                                            send("agree");
                                            if(stuff.startsWith("winner")){
                                                Toast.makeText(gActivity, Opponent + " won the game!",Toast.LENGTH_LONG).show();
                                            }
                                            else if(stuff.startsWith("tie")){
                                                Toast.makeText(gActivity, "TIE",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else if(stuff.startsWith("exit")){

                                        }
                                        else if(stuff.startsWith("playagain")){

                                        }
                                    }
                                }

                                @Override
                                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate payloadTransferUpdate) {
                                    //if stream or file, we need to know when the transfer has finished.  ignoring this right now.
                                }
                            });
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    final String endPointName = result.toString();
                    IsConnected = true;
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            ConnectedEndPointId = endpointId;
                            //if we don't then more can be added to conversation, when an List<string> of endpointIds to send to, instead a string.
                            // ... .add(endpointId);
                            stopSearching();  //and comment this out to allow more then one connection.
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            break;
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    IsConnected = false;
                    ConnectedEndPointId = "";  //need a remove if using a list.
                }

            };

    public void createConnection(String endpointId) {
        currentContext = this.currentContext;
        Nearby.getConnectionsClient(currentContext)
                .requestConnection(
                        Usernickname,   //human readable name for the local endpoint.  if null/empty, uses device name or model.
                        endpointId,
                        connectionLifecycleCallback)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                IsConnected = true;
                                if(Usernickname == "Server"){
                                    connectionLogger.setText("We found a playmate! \nPick your player sign.");
                                    linearLayout.removeView(currentActivity.findViewById(R.id.LetFindingButton));
                                    linearLayout.removeView(currentActivity.findViewById(R.id.FindFriendButton));
                                    linearLayout.removeView(currentActivity.findViewById(R.id.text));
                                    pickPlayer = new Button(currentContext);
                                    pickPlayer.setText("Choose your sign");
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 183);
                                    lp.topMargin = 25;
                                    lp.gravity = Gravity.CENTER;
                                    linearLayout.addView(pickPlayer, lp);
                                    pickPlayer.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DialogFragment ChooseXO = new X_ODialog();
                                            ChooseXO.setCancelable(true);
                                            ChooseXO.show(fragmentManager,"X or O");
                                        }
                                    });
                                }
                                else{
                                    linearLayout.removeView(currentActivity.findViewById(R.id.LetFindingButton));
                                    linearLayout.removeView(currentActivity.findViewById(R.id.FindFriendButton));
                                    linearLayout.removeView(currentActivity.findViewById(R.id.text));
                                    connectionLogger.setText("We found a playmate! \nPick your player sign.");
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                IsConnected = false;
                                e.printStackTrace();
                            }
                        });
    }

    protected void send(String data) {
        //basic error checking
        if (ConnectedEndPointId.compareTo("") == 0) { //empty string, no connection
            return;
        }

        Payload payload = Payload.fromBytes(data.getBytes());

        // sendPayload (List<String> endpointIds, Payload payload)  if more then one connection allowed.
        Nearby.getConnectionsClient(currentContext).
                sendPayload(ConnectedEndPointId,  //end point to end to
                        payload)   //the actual payload of data to send.
                .addOnSuccessListener(new OnSuccessListener<Void>() {  //don't know if need this one.
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(context,"Message send successfully.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void stopSearching() {
        mIsAdvertising = false;
        Nearby.getConnectionsClient(currentContext).stopAdvertising() ;
    }

    protected void stopBeingFind() {
        mIsDiscovering = false;
        Nearby.getConnectionsClient(currentContext).stopDiscovery();
    }

    public void startGame() {
        for (int i = 0; i < boardArray.length; i++) {
            boardArray[i] = ' ';
        }
        currentPlayer = 'X';
        isgameOver = false;
    }

}