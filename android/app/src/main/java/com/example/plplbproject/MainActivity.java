package com.example.plplbproject;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.Semestre;
import metier.UE;

import static constantes.NET.CONNEXION;
import static constantes.NET.SENDDATACONNEXION;
import static constantes.NET.SENDMESSAGE;

public class MainActivity extends AppCompatActivity {

    private ArrayList<UE> ueList;

    private ListView ueListView;

    private Socket mSocket;
    private final Gson gson = new GsonBuilder().create();

    private Semestre semestre = defaultSemester();


    private Semestre defaultSemester(){
        UE ue = new UE("En attente du serveur","...");
        ArrayList<UE> a = new ArrayList<UE>();
        a.add(ue);
        return new Semestre(-1,a);
    }

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
                Etudiant etu = new Etudiant("Etudiant 1");
                mSocket.emit(CONNEXION, gson.toJson(etu));
            }
        });
        mSocket.on(SENDMESSAGE, onNewMessage);

        mSocket.on(SENDDATACONNEXION,new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                semestre = gson.fromJson((String) args[0],Semestre.class);
                System.out.println("data receive from server");
            }
        });


        mSocket.connect();

        //###################### Adapt the list of Ue to display #####################

        //TODO: for now we only have one Ue to display, instancied here. In reality we need to get it from the server

        ueList = semestre.getListUE();


        ueListView = (ListView) findViewById(R.id.ueListView);

        UeDisplayAdapter ueDisplayAdapter = new UeDisplayAdapter(this, ueList);
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
