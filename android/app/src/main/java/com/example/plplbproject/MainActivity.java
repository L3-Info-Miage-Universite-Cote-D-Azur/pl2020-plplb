package com.example.plplbproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static constantes.NET.CONNEXION;
import static constantes.NET.SENDMESSAGE;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Ue> ueList;

    private ListView ueListView;

    private Socket mSocket;

    /**
     * Set a listener on the server to receive messages
     *
    */
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String receivedMessage = (String) args[0];

                    // add the message to view todo
                    //just show the message in a toast
                    Toast.makeText(getApplicationContext(),"Server sent you a message: "+ receivedMessage,Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("coucouDebud");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ####################### SOCKET STUFF ####################################

        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
            System.out.println("MSOCKET: " + mSocket);
            System.out.println("########### SOCKET CONNECTED ############");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit(CONNEXION, "I am the client");
            }})
                .on(SENDMESSAGE,onNewMessage);


        mSocket.connect();

        //###################### Adapt the list of Ue to display #####################

        //TODO: for now we only have one Ue to display, instancied here. In reality we need to get it from the server

        ueList = new ArrayList<Ue>();
        ueList.add(new Ue("Projet","(6666)"));
        ueList.add(new Ue("Algo","(1234)"));

        ueListView = (ListView) findViewById(R.id.ueListView);

        UeDisplayAdapter ueDisplayAdapter= new UeDisplayAdapter(this,ueList);
        ueListView.setAdapter(ueDisplayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
